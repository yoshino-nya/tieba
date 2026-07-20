package org.example.tieba.user;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.example.tieba.common.validation.AtLeastOneNotBlank;
import org.springframework.web.multipart.MultipartFile;

@Data
@AtLeastOneNotBlank(message = "至少修改一项")
public class UpdateUserRequest {
    private String nickname;

    @Email(message = "邮箱格式错误")
    private String email;
}
