package org.example.tieba.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CreateBoardRequest {
    @NotBlank(message = "吧名不能为空")
    @Size(max = 14, message = "吧名不能超过 14 个字符")
    private String name;

    @Size(max = 200, message = "吧描述不能超过 200 个字符")
    private String description;
}
