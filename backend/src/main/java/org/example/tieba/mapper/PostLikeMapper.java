package org.example.tieba.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.tieba.model.PostLike;

public interface PostLikeMapper {

    @Select("SELECT COUNT(1) FROM post_likes WHERE post_id = #{post_id} AND user_id = #{user_id}")
    boolean exists(PostLike postLike);

    // 0 表示 like，1 是 unlike，但是我们后面要 update，所以这里设置成一个没有用的值，比如说 2
    @Insert("INSERT INTO post_likes (post_id, user_id, status) VALUES (#{post_id}, #{user_id}, 2);")
    void insert(PostLike postLike);

    @Update("""
            UPDATE post_likes pl
            JOIN post p ON pl.post_id = p.id
            SET pl.status = #{status},
                p.like_count = p.like_count + IF(#{status} = 1, 1, -1)
            WHERE pl.post_id = #{post_id} AND pl.user_id = #{user_id}
            AND pl.status != #{status}
            """)
    void update(PostLike postLike);
}
