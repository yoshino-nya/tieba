package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.common.Result;
import org.example.tieba.dto.UpdateUserRequest;
import org.example.tieba.dto.UserDetailResponse;
import org.example.tieba.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/me")
    public Result<UserDetailResponse> updateProfile(@Valid @RequestBody UpdateUserRequest req) {
        return Result.success(userService.updateProfile(req));
    }

    @PostMapping("/me/avatar")
    public Result<String> updateAvatar(@RequestParam("file") MultipartFile file) {
        return Result.success(userService.updateAvatar(file));
    }
}
