package org.example.tieba.dto;

import lombok.Data;
import org.example.tieba.validation.AtLeastOneNotBlank;

@Data
@AtLeastOneNotBlank(message = "标题和内容不能都为空")
public class UpdatePostRequest {
    private String title;
    private String content;

    public void trimToNull() {
        if (title != null && title.isBlank()) title = null;
        if (content != null && content.isBlank()) content = null;
    }
}
