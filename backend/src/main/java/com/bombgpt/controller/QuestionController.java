package com.bombgpt.controller;

import com.bombgpt.common.Result;
import com.bombgpt.service.DeepSeekService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {

    private final DeepSeekService deepSeekService;

    // 构造器注入
    public QuestionController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @GetMapping("/ask")
    public Result<String> ask(@RequestParam("question") String question) {

        String answer = deepSeekService.chat(question);

        return Result.success(answer);
    }
}