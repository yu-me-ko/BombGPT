package com.bombgpt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bombgpt.common.Result;
import com.bombgpt.entity.Knowledge;
import com.bombgpt.mapper.KnowledgeMapper;

@CrossOrigin
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    private final KnowledgeMapper knowledgeMapper;

    public KnowledgeController(KnowledgeMapper knowledgeMapper) {
        this.knowledgeMapper = knowledgeMapper;
    }

    @GetMapping("/list")
    public Result<List<Knowledge>> list() {
        return Result.success(knowledgeMapper.list());
    }

    @GetMapping("/search")
    public Result<List<Knowledge>> search(@RequestParam("keyword") String keyword) {
        return Result.success(knowledgeMapper.search(keyword));
    }
}