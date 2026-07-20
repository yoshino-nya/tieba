package org.example.tieba.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.tieba.common.Result;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "编辑个人资料", description = "修改昵称和邮箱，两个字段至少填一个")
    @PatchMapping("/me")
    public Result<UserDetailResponse> updateProfile(@Valid @RequestBody UpdateUserRequest req) {
        return Result.success(userService.updateProfile(req));
    }

    @Operation(summary = "上传头像", description = "上传图片作为新头像，格式 jpg/png/gif/webp，大小不超过 5MB")
    @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> updateAvatar(
            @Parameter(description = "头像图片文件") @RequestParam("file") MultipartFile file) {
        return Result.success(userService.updateAvatar(file));
    }
}
