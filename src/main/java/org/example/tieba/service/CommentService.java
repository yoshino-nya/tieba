package org.example.tieba.service;

import org.example.tieba.dto.CreateCommentRequest;
import org.example.tieba.model.Comment;
import org.example.tieba.util.SecurityUtil;

public class CommentService {
    private final SecurityUtil securityUtil;

    public CommentService(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    public void create(CreateCommentRequest req) {
        Comment comment = new Comment();
        comment.setUserId(securityUtil.getCurrentUserId());
        comment.setContent(req.getContent());
        comment.setPostId(req.getPostId());
        comment.setParentId(req.getParentId());
    }
}
