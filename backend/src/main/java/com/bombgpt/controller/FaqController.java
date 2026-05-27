package com.bombgpt.controller;

import com.bombgpt.common.Result;
import com.bombgpt.entity.Faq;
import com.bombgpt.mapper.FaqMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RestController
public class FaqController {

    @Autowired
    private FaqMapper faqMapper;

    @GetMapping("/faq/list")
    public Result<List<Faq>> list() {
        return Result.success(faqMapper.list());
    }
}