package com.bombgpt.service;

import com.bombgpt.entity.Knowledge;
import com.bombgpt.mapper.KnowledgeMapper;
import com.bombgpt.mapper.AskLogMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DeepSeekService {

    private final KnowledgeMapper knowledgeMapper;
    private final AskLogMapper askLogMapper;

    public DeepSeekService(
            KnowledgeMapper knowledgeMapper,
            AskLogMapper askLogMapper
    ) {
        this.knowledgeMapper = knowledgeMapper;
        this.askLogMapper = askLogMapper;
    }

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.api.model}")
    private String model;

    public String chat(String question, Boolean deep) {

        boolean deepSearch = Boolean.TRUE.equals(deep) || question.contains("深度搜索");
        String realQuestion = question.replace("深度搜索", "").trim();

        List<Knowledge> knowledgeList;

        if (deepSearch) {
            knowledgeList = knowledgeMapper.searchAll(realQuestion);
        } else {
            knowledgeList = knowledgeMapper.search(realQuestion);
        }

        if (knowledgeList == null || knowledgeList.isEmpty()) {
            knowledgeList = searchByAllKnowledgeKeywords(realQuestion, deepSearch);
        }

        if (knowledgeList == null || knowledgeList.isEmpty()) {
            String answer = "数据库中暂时没有相关信息。请尝试更换关键词或开启深度搜索。";
            askLogMapper.insert(question, answer);
            return answer;
        }

        try {
            String answer = callDeepSeek(realQuestion, knowledgeList, deepSearch);

            increaseViewCountForList(knowledgeList);
            askLogMapper.insert(question, answer);

            return answer;

        } catch (Exception e) {
            String localAnswer = findLocalAnswer(realQuestion, knowledgeList);

            if (localAnswer != null) {
                askLogMapper.insert(question, localAnswer);
                return localAnswer;
            }

            String answer = "数据库中暂时没有相关信息。";
            askLogMapper.insert(question, answer);
            return answer;
        }
    }

    private List<Knowledge> searchByAllKnowledgeKeywords(String question, boolean deepSearch) {
        List<Knowledge> allKnowledgeList = knowledgeMapper.listAll();
        List<Knowledge> resultList = new ArrayList<>();

        for (Knowledge knowledge : allKnowledgeList) {
            if (matchKeyword(question, knowledge.getKeywords())) {
                resultList.add(knowledge);
            }

            if (!deepSearch && resultList.size() >= 5) {
                break;
            }
        }

        return resultList;
    }

    private void increaseViewCountForList(List<Knowledge> knowledgeList) {
        for (Knowledge knowledge : knowledgeList) {
            knowledgeMapper.increaseViewCount(knowledge.getId());
        }
    }

    private String findLocalAnswer(String question, List<Knowledge> knowledgeList) {
        for (Knowledge knowledge : knowledgeList) {
            if (question.contains(knowledge.getQuestion())
                    || knowledge.getQuestion().contains(question)
                    || matchKeyword(question, knowledge.getKeywords())) {
                knowledgeMapper.increaseViewCount(knowledge.getId());
                return knowledge.getAnswer();
            }
        }
        return null;
    }

    private boolean matchKeyword(String question, String keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return false;
        }

        String[] keywordArray = keywords.split("[,，、;；\\s]+");

        for (String keyword : keywordArray) {
            if (!keyword.isBlank() && question.contains(keyword.trim())) {
                return true;
            }
        }

        return false;
    }

    private String callDeepSeek(String question, List<Knowledge> knowledgeList, boolean deepSearch) {
        StringBuilder knowledgeText = new StringBuilder();

        for (Knowledge knowledge : knowledgeList) {
            knowledgeText.append("问题：").append(knowledge.getQuestion()).append("\n");
            knowledgeText.append("答案：").append(knowledge.getAnswer()).append("\n");
            knowledgeText.append("关键词：").append(knowledge.getKeywords()).append("\n\n");
        }

        String modePrompt = deepSearch
                ? "当前已开启深度搜索模式。请综合所有候选知识，进行更完整、更细致的回答。"
                : "当前为普通搜索模式。请基于候选知识，简洁准确地回答。";

        String prompt = "你是华南理工大学校园生活百事通助手。"
                + modePrompt
                + "请严格根据下面的校园知识库回答用户问题，不要编造信息。"
                + "回答时不要以“根据知识库信息”“根据提供的信息”等类似句子开头，"
                + "请直接给出答案内容。"
                + "如果知识库中有相关内容，请在回答最后加上“（根据知识库信息）”。"
                + "如果知识库没有相关内容，就只回答：数据库中暂时没有相关信息。\n\n"
                + "【校园知识库】\n"
                + knowledgeText
                + "【用户问题】\n"
                + question;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", prompt));
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

        Map responseBody = response.getBody();
        List choices = (List) responseBody.get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");

        return message.get("content").toString();
    }
}