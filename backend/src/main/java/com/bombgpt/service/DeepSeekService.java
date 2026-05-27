package com.bombgpt.service;

import com.bombgpt.entity.Knowledge;
import com.bombgpt.mapper.KnowledgeMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DeepSeekService {

    private final KnowledgeMapper knowledgeMapper;

    public DeepSeekService(KnowledgeMapper knowledgeMapper) {
        this.knowledgeMapper = knowledgeMapper;
    }

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.api.model}")
    private String model;

    public String chat(String question) {
        List<Knowledge> knowledgeList = knowledgeMapper.search(question);

        if (knowledgeList == null || knowledgeList.isEmpty()) {
            knowledgeList = knowledgeMapper.list();
        }

        String localAnswer = findLocalAnswer(question, knowledgeList);

        try {
            return callDeepSeek(question, knowledgeList);
        } catch (Exception e) {
            if (localAnswer != null) {
                return localAnswer;
            }
            return "数据库中暂时没有相关信息。";
        }
    }

    private String findLocalAnswer(String question, List<Knowledge> knowledgeList) {
        for (Knowledge knowledge : knowledgeList) {
            if (question.contains(knowledge.getQuestion())
                    || knowledge.getQuestion().contains(question)
                    || matchKeyword(question, knowledge.getKeywords())) {
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

    private String callDeepSeek(String question, List<Knowledge> knowledgeList) {
        StringBuilder knowledgeText = new StringBuilder();

        for (Knowledge knowledge : knowledgeList) {
            knowledgeText.append("问题：").append(knowledge.getQuestion()).append("\n");
            knowledgeText.append("答案：").append(knowledge.getAnswer()).append("\n");
            knowledgeText.append("关键词：").append(knowledge.getKeywords()).append("\n\n");
        }

        String prompt = "你是华南理工大学校园生活百事通助手。"
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