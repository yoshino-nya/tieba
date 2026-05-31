package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.common.Result;
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
    public Result<PostResponse> post(@PathVariable Long boardId, @Valid @RequestBody PostRequest req) {
        return Result.success(postService.post(boardId, req));
    }

    @GetMapping("/api/boards/{boardId}/posts")
    public Result<List<PostResponse>> listPostsByBoard(@PathVariable Long boardId) {
        return Result.success(postService.listPostsByBoard(boardId));
    }

    @GetMapping("/api/users/{userId}/posts")
    public Result<List<PostResponse>> listPostsByUserId(@PathVariable Long userId) {
        return Result.success(postService.listPostsByUser(userId));
    }

    @PatchMapping("/api/posts/{postId}")
    public Result<Void> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest req) {
        // 校验至少一个字段有值，并将空白字符串视为 null 处理
        postService.updatePost(postId, req);
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/api/posts/{postId}")
    public Result<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return Result.success("删除成功", null);
    }
}
