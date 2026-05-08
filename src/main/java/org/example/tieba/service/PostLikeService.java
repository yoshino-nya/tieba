package org.example.tieba.service;

import org.example.tieba.mapper.PostLikeMapper;
import org.example.tieba.model.PostLike;
import org.example.tieba.util.SecurityUtil;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    private final PostLikeMapper postLikeMapper;
    private final SecurityUtil securityUtil;

    public PostLikeService(PostLikeMapper postLikeMapper, SecurityUtil securityUtil) {
        this.postLikeMapper = postLikeMapper;
        this.securityUtil = securityUtil;
    }

    public void toggleLikePost(Long postId, byte status) {
        PostLike postLike = new PostLike();
        postLike.setPost_id(postId);
        postLike.setUser_id(securityUtil.getCurrentUserId());
        postLike.setStatus(status);

        if(!postLikeMapper.exists(postLike)){
            postLikeMapper.insert(postLike);
        }

        postLikeMapper.update(postLike);
    }
}
