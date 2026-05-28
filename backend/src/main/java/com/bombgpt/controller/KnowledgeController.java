package com.bombgpt.controller;

import com.bombgpt.common.Result;
import com.bombgpt.entity.Knowledge;
import com.bombgpt.mapper.KnowledgeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "知识库接口")
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    private final KnowledgeMapper knowledgeMapper;

    public KnowledgeController(KnowledgeMapper knowledgeMapper) {
        this.knowledgeMapper = knowledgeMapper;
    }

    @Operation(summary = "获取知识库列表")
    @GetMapping("/list")
    public Result<List<Knowledge>> list() {
        return Result.success(knowledgeMapper.list());
    }

    @Operation(summary = "搜索知识库")
    @GetMapping("/search")
    public Result<List<Knowledge>> search(@RequestParam("keyword") String keyword) {
        return Result.success(knowledgeMapper.search(keyword));
    }
    
    @Operation(summary = "热门问题排行榜")
    @GetMapping("/hot")
    public Result<List<Knowledge>> hotList() {
        return Result.success(knowledgeMapper.hotList());
    }
}