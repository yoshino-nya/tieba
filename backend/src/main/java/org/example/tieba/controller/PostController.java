package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.annotation.RateLimit;
import org.example.tieba.common.PageResult;
import org.example.tieba.common.Result;
import org.example.tieba.dto.*;
import org.example.tieba.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RateLimit(key = "post", limit = 1, window = 60)
    @PostMapping("/api/boards/{boardId}/posts")
    public Result<PostResponse> post(@PathVariable Long boardId, @Valid @RequestBody PostRequest req) {
        return Result.success(postService.post(boardId, req));
    }

    @GetMapping("/api/boards/{boardId}/posts")
    public Result<PageResult<PostResponse>> listPostsByBoard(@PathVariable Long boardId, @Valid PageParam pageParam) {
        return Result.success(postService.listPostsByBoard(boardId, pageParam));
    }

    @GetMapping("/api/users/{userId}/posts")
    public Result<PageResult<PostResponse>> listPostsByUserId(@PathVariable Long userId, @Valid PageParam pageParam) {
        return Result.success(postService.listPostsByUser(userId, pageParam));
    }

    @GetMapping("/api/posts/{postId}")
    public Result<PostDetailResponse> getDetail(@PathVariable Long postId) {
        return Result.success(postService.getDetail(postId));
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
