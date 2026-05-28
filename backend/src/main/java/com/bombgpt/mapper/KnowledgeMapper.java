package com.bombgpt.mapper;

import com.bombgpt.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeMapper {

        @Select("""
            select *
            from knowledge
            where status = 1
            order by view_count desc
            limit 20
            """)
        List<Knowledge> list();

        @Select("""
            select *
            from knowledge
            where status = 1
              and (
                    question like concat('%', #{keyword}, '%')
                 or answer like concat('%', #{keyword}, '%')
                 or keywords like concat('%', #{keyword}, '%')
              )
            order by view_count desc
            limit 5
            """)
        List<Knowledge> search(String keyword);

        @Select("""
                select *
                from knowledge
                where status = 1
                and (
                        question like concat('%', #{keyword}, '%')
                or answer like concat('%', #{keyword}, '%')
                or keywords like concat('%', #{keyword}, '%')
                )
                order by view_count desc
                """)
        List<Knowledge> searchAll(String keyword);

        @Select("""
                select *
                from knowledge
                where status = 1
                order by view_count desc
                limit 10
                """)       
        List<Knowledge> hotList();

        @org.apache.ibatis.annotations.Update("""
                update knowledge
                set view_count = view_count + 1
                where id = #{id}
                """)
        void increaseViewCount(Long id);

        @Select("""
                select *
                from knowledge
                where status = 1
                """)
        List<Knowledge> listAll();
}