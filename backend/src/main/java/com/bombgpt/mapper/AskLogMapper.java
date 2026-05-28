package com.bombgpt.mapper;

import com.bombgpt.entity.AskLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AskLogMapper {

    @Insert("""
            insert into ask_log(question, answer)
            values(#{question}, #{answer})
            """)
    void insert(String question, String answer);

    @Select("""
            select *
            from ask_log
            order by create_time desc
            limit 20
            """)
    List<AskLog> list();
    
    @Select("""
            select question, count(*) as count
            from ask_log
            group by question
            order by count desc
            limit 10
            """)
    List<java.util.Map<String, Object>> questionRank();
}