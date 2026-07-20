package org.example.tieba.auth;
// service/AuthService

import org.example.tieba.common.ErrorCode;
import org.example.tieba.common.BusinessException;
import org.example.tieba.user.UserMapper;
import org.example.tieba.user.User;
import org.example.tieba.infra.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public AuthService(PasswordEncoder passwordEncoder, UserMapper userMapper, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(LoginRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if(user == null) {
            throw new BusinessException(ErrorCode.INVALID_USER_CREDENTIALS, "用户名或密码错误");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_USER_CREDENTIALS, "用户名或密码错误");
        }
        String token = jwtUtil.generateToken(user.getUsername(), user.getId());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getId());
    }

    public AuthResponse register(RegisterRequest request) {
        if(userMapper.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "用户名已被使用");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userMapper.insert(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getId());
        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getId());
    }
}