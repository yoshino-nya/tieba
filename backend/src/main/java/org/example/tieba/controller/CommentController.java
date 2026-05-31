package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.common.Result;
import org.example.tieba.dto.CommentResponse;
import org.example.tieba.dto.CreateCommentRequest;
import org.example.tieba.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/posts/{postId}/comments")
    public Result<CommentResponse> create(@Valid @RequestBody CreateCommentRequest req, @PathVariable Long postId) {
        return Result.success(commentService.create(req, postId));
    }

    @GetMapping("/api/posts/{postId}/comments")
    public Result<List<CommentResponse>> list(@PathVariable Long postId) {
        return Result.success(commentService.list(postId));
    }

    @DeleteMapping("/api/comments/{commentId}")
    public Result<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return Result.success("删除成功", null);
    }
}
