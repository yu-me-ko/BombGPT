package com.bombgpt.controller;

import com.bombgpt.mapper.FaqMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/faq")
public class FaqController {

    private final FaqMapper faqMapper;

    public FaqController(FaqMapper faqMapper) {
        this.faqMapper = faqMapper;
    }

    @GetMapping("/list")
    public List<Map<String, Object>> list() {
        return faqMapper.list();
    }
}
