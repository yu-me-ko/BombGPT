package com.bombgpt.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface FaqMapper {

    @Select("select * from faq")
    List<Map<String, Object>> list();
}
