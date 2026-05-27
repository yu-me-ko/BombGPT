package com.bombgpt.mapper;

import com.bombgpt.entity.Faq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FaqMapper {

    @Select("select * from faq")
    List<Faq> list();

    @Select("""
            select * from faq
            where question like concat('%', #{keyword}, '%')
               or answer like concat('%', #{keyword}, '%')
            limit 5
            """)
    List<Faq> searchByKeyword(String keyword);
}