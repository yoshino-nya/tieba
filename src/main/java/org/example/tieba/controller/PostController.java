package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.dto.*;
import org.example.tieba.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/boards/{boardId}/posts")
    public ResponseEntity<ApiResponse<PostResponse>> post(@PathVariable Long boardId, @Valid @RequestBody PostRequest req) {
        return ResponseEntity.ok(ApiResponse.success(postService.post(boardId, req)));
    }

    @GetMapping("/api/boards/{boardId}/posts")
    ResponseEntity<ApiResponse<List<PostResponse>>> listPostsByBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(ApiResponse.success(postService.listPostsByBoard(boardId)));
    }

    @GetMapping("/api/users/{userId}/posts")
    ResponseEntity<ApiResponse<List<PostResponse>>> listPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(postService.listPostsByUser(userId)));
    }

    @PatchMapping("/api/posts/{postId}")
    ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest req) {
        // 校验至少一个字段有值，并将空白字符串视为 null 处理
        req.trimToNull();
        postService.updatePost(postId, req);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/api/posts/{postId}")
    ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
