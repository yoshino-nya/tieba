package org.example.tieba.controller;

import org.example.tieba.common.Result;
import org.example.tieba.service.PostLikeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostLikeController {
    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/api/posts/{postId}/like")
    public Result<Void> likePost(@PathVariable Long postId) {
        postLikeService.toggleLikePost(postId, (byte) 1);
        return Result.success("点赞成功", null);
    }

    @PostMapping("/api/posts/{postId}/unlike")
    public Result<Void> unlikePost(@PathVariable Long postId) {
        postLikeService.toggleLikePost(postId, (byte) 0);
        return Result.success("取消点赞成功", null);
    }
}
