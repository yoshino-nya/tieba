package org.example.tieba.comment;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {
    @Select("""
            SELECT
                id,
                user_id AS userId,
                content,
                post_id AS postId,
                root_id AS rootId,
                parent_id AS parent_ID,
                status,
                created_at AS createdAt
            FROM comment
            WHERE id = #{id} AND status = 0
            """)
    Comment selectById(Long id);


    @Insert("""
                INSERT INTO comment
                (user_id, content, post_id, root_id, parent_id, status, created_at)
                VALUES (#{userId}, #{content}, #{postId}, #{rootId}, #{parentId}, #{status}, #{createdAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Comment comment);

    List<CommentResponse> selectByPostId(Long postId);

    @Update("UPDATE comment SET status = 1 WHERE id = #{id}")
    void deleteById(Long id);

    @Update("UPDATE comment SET root_id = id WHERE id = #{id}")
    void setRootId(Long id);

    @Select("SELECT user_id FROM comment WHERE id = #{id} AND status = 0")
    Long selectUserIdById(Long id);
}
