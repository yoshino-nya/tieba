package org.example.tieba.service;

import org.example.tieba.constants.ErrorCodeConstants;
import org.example.tieba.dto.BoardResponse;
import org.example.tieba.dto.PostRequest;
import org.example.tieba.dto.PostResponse;
import org.example.tieba.dto.UpdatePostRequest;
import org.example.tieba.exception.BusinessException;
import org.example.tieba.mapper.PostMapper;
import org.example.tieba.model.Post;
import org.example.tieba.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    private final PostMapper postMapper;
    private final SecurityUtil securityUtil;

    public PostService(PostMapper postMapper, SecurityUtil securityUtil) {
        this.postMapper = postMapper;
        this.securityUtil = securityUtil;
    }

    public PostResponse post(Long boardId, PostRequest req) {
        Post post = new Post();

        post.setUser_id(securityUtil.getCurrentUserId());
        post.setBoard_id(boardId);

        post.setTitle(req.getTitle());
        post.setContent(req.getContent());

        post.setLike_count(0L);

        LocalDateTime now = LocalDateTime.now();
        post.setCreated_at(now);
        post.setUpdated_at(now);
        post.setStatus((byte) 0);

        postMapper.insert(post);
        return new PostResponse(post);
    }

    public List<PostResponse> listPostsByBoard(Long boardId) {
        return postMapper.selectByBoardId(boardId);
    }

    public List<PostResponse> listPostsByUser(Long userId) {
        return postMapper.selectByUserId(userId);
    }

    public void updatePost(Long postId, UpdatePostRequest req) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException(ErrorCodeConstants.NOT_FOUND, "帖子不存在");
        }
        if (!Objects.equals(post.getUser_id(), securityUtil.getCurrentUserId())) {
            throw new BusinessException(ErrorCodeConstants.FORBIDDEN, "无权限修改此帖子");
        }
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setUpdated_at(LocalDateTime.now());
        postMapper.update(post);
    }

    public void deletePost(Long postId) {
        Long userId = postMapper.selectUserByPostId(postId);
        if (userId == null) {
            throw new BusinessException(ErrorCodeConstants.NOT_FOUND, "帖子不存在");
        }


        if (!Objects.equals(userId, securityUtil.getCurrentUserId())) {
            throw new BusinessException(ErrorCodeConstants.FORBIDDEN, "无权限删除此帖子");
        }
        postMapper.deleteById(postId);
    }
}
