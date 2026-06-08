package org.example.tieba.mapper;

import org.apache.ibatis.annotations.*;
import org.example.tieba.dto.UpdateUserRequest;
import org.example.tieba.dto.UserBrief;
import org.example.tieba.dto.UserDetailResponse;
import org.example.tieba.model.User;

public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT COUNT(1) FROM users WHERE username = #{username}")
    boolean existsByUsername(String username);

    @Select("SELECT id, username, nickname, avatar FROM users WHERE id = #{id}")
    UserBrief findBriefById(Long id);

    @Insert("""
                INSERT INTO users(username, email, password)
                VALUES (#{username}, #{email}, #{password})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT id, username, nickname, avatar, email FROM users WHERE id = #{id}")
    UserDetailResponse selectDetailById(Long id);

    @Update("UPDATE users SET nickname = #{nickname}, email = #{email} WHERE id = #{id} ")
    void update(UserDetailResponse req);

    @Update("UPDATE users SET avatar = #{avatar} WHERE id = #{id}")
    void updateAvatar(String avatar, Long id);
}
