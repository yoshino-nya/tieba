package org.example.tieba.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.tieba.dto.BoardResponse;
import org.example.tieba.model.Board;

import java.util.List;

public interface BoardMapper {
    @Select("SELECT * FROM boards WHERE name = #{name}")
    Board findByName(String name);

    @Select("SELECT COUNT(1) FROM boards WHERE name = #{name}")
    boolean existsByName(String name);

    @Insert("""
                    INSERT INTO boards (name, description, manager_id, created_at)
                    VALUES (#{name},#{description}, #{manager_id}, #{created_at})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Board board);

    @Select("""
                SELECT id, CONCAT(name, '吧'), description, manager_id, created_at
                FROM boards
            """)
    List<BoardResponse> selectAll();
}
