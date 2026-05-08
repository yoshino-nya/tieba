package org.example.tieba.controller;

import org.example.tieba.dto.ApiResponse;
import org.example.tieba.service.PostLikeService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        postLikeService.toggleLikePost(postId, (byte) 1);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/api/posts/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId) {
        postLikeService.toggleLikePost(postId, (byte) 0);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
