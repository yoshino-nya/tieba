package org.example.tieba.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.tieba.model.User;

public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT COUNT(1) FROM users WHERE username = #{username}")
    boolean existsByUsername(String username);

    @Insert("""
                INSERT INTO users(username, email, password)
                VALUES (#{username}, #{email}, #{password})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
}
