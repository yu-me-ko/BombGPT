package com.bombgpt.controller;

import com.bombgpt.common.Result;
import com.bombgpt.entity.AskLog;
import com.bombgpt.mapper.AskLogMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "问答日志接口")
@RestController
@RequestMapping("/log")
public class AskLogController {

    private final AskLogMapper askLogMapper;

    public AskLogController(AskLogMapper askLogMapper) {
        this.askLogMapper = askLogMapper;
    }

    @Operation(summary = "最近问答日志")
    @GetMapping("/list")
    public Result<List<AskLog>> list() {
        return Result.success(askLogMapper.list());
    }

    @Operation(summary = "用户提问排行榜")
    @GetMapping("/rank")
    public Result<List<java.util.Map<String, Object>>> rank() {
        return Result.success(askLogMapper.questionRank());
}
}