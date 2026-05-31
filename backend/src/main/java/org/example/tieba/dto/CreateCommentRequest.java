package org.example.tieba.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateCommentRequest {
    @NotBlank(message = "评论内容不能为空")
    private String content;

    private Long parentId;
}
