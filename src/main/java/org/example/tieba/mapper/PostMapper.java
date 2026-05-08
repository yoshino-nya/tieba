package org.example.tieba.mapper;

import org.apache.ibatis.annotations.*;
import org.example.tieba.dto.PostResponse;
import org.example.tieba.model.Post;

import java.util.List;

public interface PostMapper {

    // id, like_count, status 用 default
    @Insert("""
                INSERT INTO post
                (user_id, board_id, title, content, created_at, updated_at)
                VALUES (#{user_id}, #{board_id}, #{title}, #{content}, #{created_at}, #{updated_at})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Post post);

    void update(Post post);

    @Select("""
                SELECT id, user_id, board_id, title, content, like_count, created_at, updated_at, status
                FROM post
                WHERE board_id = #{boardId} AND status = 0
                ORDER BY id DESC
            """)
    List<PostResponse> selectByBoardId(Long boardId);

    @Select("SELECT user_id FROM post WHERE id = #{postId}")
    Long selectUserByPostId(Long postId);

    @Select("""
                SELECT id, user_id, board_id, title, content, like_count, created_at, updated_at, status
                FROM post
                WHERE user_id = #{userId} AND status = 0
                ORDER BY id DESC
            """)
    List<PostResponse> selectByUserId(Long userId);

    // status 为 1 表示帖子被删除，0 正常，2 隐藏
    @Select("""
            SELECT id, user_id, board_id, title, content, like_count, created_at, updated_at, status
            FROM post
            WHERE id = #{id} AND status != 1
            """)
    Post selectById(Long id);

    @Update("""
                UPDATE post
                SET status = 1
                WHERE id = #{postId}
            """)
    void deleteById(Long postId);
}
