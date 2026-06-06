package org.example.tieba.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.tieba.dto.PostDetailResponse;
import org.example.tieba.dto.PostResponse;
import org.example.tieba.model.Post;

import java.util.List;

public interface PostMapper {

    // id, like_count, status 用 default
    @Insert("""
                INSERT INTO post
                (user_id, board_id, title, content, created_at, updated_at)
                VALUES (#{userId}, #{boardId}, #{title}, #{content}, #{createdAt}, #{updatedAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Post post);

    @Select("SELECT COUNT(*) FROM post WHERE board_id = #{boardId} AND status = 0")
    int countByBoardId(Long boardId);

    @Select("SELECT COUNT(*) FROM post WHERE user_id = #{user_id} AND status = 0")
    int countByUserId(Long userId);

    PostDetailResponse selectDetailById(Long id, Long currentUserId);

    void update(Post post);

    List<PostResponse> selectByBoardId(Long boardId, Long currentUserId, int offset, int size);

    @Select("SELECT user_id FROM post WHERE id = #{postId}")
    Long selectUserByPostId(Long postId);

    List<PostResponse> selectByUserId(Long userId, Long currentUserId, int offset, int size);

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
