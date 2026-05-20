package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.dto.ApiResponse;
import org.example.tieba.dto.CommentResponse;
import org.example.tieba.dto.CreateCommentRequest;
import org.example.tieba.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> create(@Valid @RequestBody   CreateCommentRequest req, @PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.success(commentService.create(req, postId)));
    }

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> list(@PathVariable Long postId) {
        return ResponseEntity.ok(ApiResponse.success(commentService.list(postId)));
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
