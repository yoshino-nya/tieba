package org.example.tieba.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.example.tieba.dto.BoardMemberResponse;
import org.example.tieba.model.BoardMember;

import java.util.List;

public interface BoardMemberMapper {

    @Insert("""
                INSERT INTO board_members(board_id, user_id, role, joined_at)
                VALUES (#{board_id}, #{user_id}, #{role}, #{joined_at})
            """)
    void insert(BoardMember boardMember);

    @Delete("DELETE FROM board_members WHERE user_id = #{id}")
    void remove(Long id);

    @Select("""
        SELECT
            u.id AS userId,
            u.username,
            u.nickname,
            u.avatar,
            u.email,
            bm.role,
            bm.joined_at AS joinedAt
        FROM board_members bm
        JOIN users u ON bm.user_id = u.id
        WHERE bm.board_id = #{boardId}
    """)
    List<BoardMemberResponse> selectMembers(Long boardId);
}
