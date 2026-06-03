package org.example.tieba.service;

import lombok.extern.slf4j.Slf4j;
import org.example.tieba.constants.ErrorCode;
import org.example.tieba.dto.*;
import org.example.tieba.exception.BusinessException;
import org.example.tieba.mapper.PostMapper;
import org.example.tieba.mapper.UserMapper;
import org.example.tieba.model.Post;
import org.example.tieba.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PostService {

    private final PostMapper postMapper;
    private final SecurityUtil securityUtil;
    private final UserMapper userMapper;

    public PostService(PostMapper postMapper, SecurityUtil securityUtil, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.securityUtil = securityUtil;
        this.userMapper = userMapper;
    }

    public PostResponse post(Long boardId, PostRequest req) {
        Post post = new Post();

        post.setUserId(securityUtil.getCurrentUserId());
        post.setBoardId(boardId);

        post.setTitle(req.getTitle());
        post.setContent(req.getContent());

        post.setLikeCount(0L);

        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.setStatus((byte) 0);

        postMapper.insert(post);
        PostResponse postResponse = new PostResponse(post);
        postResponse.setAuthor(userMapper.findBriefById(securityUtil.getCurrentUserId()));
        return postResponse;
    }

    public List<PostResponse> listPostsByBoard(Long boardId) {
        return postMapper.selectByBoardId(boardId);
    }

    public List<PostResponse> listPostsByUser(Long userId) {
        return postMapper.selectByUserId(userId);
    }

    public void updatePost(Long postId, UpdatePostRequest req) {
        Post post = postMapper.selectById(postId);
        log.info("查到帖子: {}", post);
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "帖子不存在");
        }
        if (!Objects.equals(post.getUserId(), securityUtil.getCurrentUserId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN, post.getUserId() + " " + securityUtil.getCurrentUserId() + "无权限修改此帖子");
        }
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setUpdatedAt(LocalDateTime.now());
        postMapper.update(post);
    }

    public void deletePost(Long postId) {
        Long userId = postMapper.selectUserByPostId(postId);
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "帖子不存在");
        }


        if (!Objects.equals(userId, securityUtil.getCurrentUserId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权限删除此帖子");
        }
        postMapper.deleteById(postId);
    }
}
