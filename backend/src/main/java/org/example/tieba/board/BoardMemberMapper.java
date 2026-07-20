package org.example.tieba.board;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoardMemberMapper {

    @Insert("""
                INSERT INTO board_members(board_id, user_id, role, joined_at)
                VALUES (#{board_id}, #{user_id}, #{role}, #{joined_at})
            """)
    void insert(BoardMember boardMember);

    @Delete("DELETE FROM board_members WHERE board_id = #{boardId} AND user_id = #{userId}")
    void remove(Long boardId, Long userId);

    @Select("""
        SELECT
            u.id AS userId,
            u.username,
            u.nickname,
            u.avatar
        FROM board_members bm
        JOIN users u ON u.id = bm.user_id
        WHERE bm.board_id = #{boardId}
    """)
    List<BoardMemberResponse> selectMembers(Long boardId);
}
