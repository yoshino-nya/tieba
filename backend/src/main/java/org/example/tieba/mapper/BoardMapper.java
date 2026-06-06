package org.example.tieba.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.tieba.dto.BoardDetailResponse;
import org.example.tieba.dto.BoardResponse;
import org.example.tieba.model.Board;

import java.util.List;

public interface BoardMapper {
    @Select("SELECT * FROM boards WHERE name = #{name}")
    Board findByName(String name);

    @Select("SELECT COUNT(1) FROM boards WHERE name = #{name}")
    boolean existsByName(String name);

    @Select("SELECT COUNT(*) FROM boards ")
    int count();

    @Insert("""
                    INSERT INTO boards (name, description, manager_id, created_at)
                    VALUES (#{name},#{description}, #{manager_id}, #{created_at})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Board board);

    // 每个吧的成员数量和帖子数量
    @Select("""
                SELECT b.id, CONCAT(b.name, '吧') AS name, b.description, b.manager_id, b.created_at,
                (
                    SELECT COUNT(*) FROM board_members bm
                    WHERE bm.board_id = b.id
                ) AS member_count,
                (
                    SELECT COUNT(*) FROM post p
                    WHERE p.board_id = b.id
                ) AS post_count
                FROM boards b
                LIMIT #{offset}, #{limit}
            """)
    List<BoardResponse> selectAll(int offset, int limit);

    BoardDetailResponse selectDetailById(Long id);
}
