package com.bombgpt.controller;

import com.bombgpt.common.Result;
import com.bombgpt.service.DeepSeekService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI问答接口")
@RestController
@RequestMapping("/question")
public class QuestionController {

    private final DeepSeekService deepSeekService;

    public QuestionController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @Operation(summary = "校园智能问答")
    @GetMapping("/ask")
    public Result<String> ask(
            @RequestParam("question") String question,
            @RequestParam(value = "deep", defaultValue = "false") Boolean deep
    ) {
        String answer = deepSeekService.chat(question, deep);
        return Result.success(answer);
    }
}