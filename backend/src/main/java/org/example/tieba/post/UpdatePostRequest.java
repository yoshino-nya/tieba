package org.example.tieba.post;

import lombok.Data;
import lombok.Getter;
import org.example.tieba.common.validation.AtLeastOneNotBlank;

@Getter
@AtLeastOneNotBlank(message = "标题和内容不能都为空")
public class UpdatePostRequest {
    private String title;
    private String content;

    public void setTitle(String title) {
        this.title = (title == null || title.isBlank()) ? null : title.trim();
    }

    public void setContent(String content) {
        this.content = (content == null || content.isBlank()) ? null : content.trim();
    }
}
