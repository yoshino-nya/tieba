package org.example.tieba.post;

import lombok.extern.slf4j.Slf4j;
import org.example.tieba.common.PageResult;
import org.example.tieba.common.ErrorCode;
import org.example.tieba.common.dto.PageParam;
import org.example.tieba.common.BusinessException;
import org.example.tieba.user.UserMapper;
import org.example.tieba.infra.security.SecurityUtil;
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

    public PostDetailResponse getDetail(Long postId) {
        return postMapper.selectDetailById(postId, securityUtil.getCurrentUserId());
    }

    public PageResult<PostResponse> listPostsByBoard(Long boardId, PageParam pageParam) {
        int current = pageParam.getCurrent();
        int size = pageParam.getSize();
        int offset = (current - 1) * size;
        List<PostResponse> list = postMapper.selectByBoardId(boardId, securityUtil.getCurrentUserId(), offset, size);
        return new PageResult<>(postMapper.countByBoardId(boardId), pageParam.getCurrent(), pageParam.getSize(), list);
    }

    public PageResult<PostResponse> listPostsByUser(Long userId, PageParam pageParam) {
        int current = pageParam.getCurrent();
        int size = pageParam.getSize();
        int offset = (current - 1) * size;
        List<PostResponse> list = postMapper.selectByUserId(userId, securityUtil.getCurrentUserId(), offset, size);
        return new PageResult<>(postMapper.countByUserId(userId), pageParam.getCurrent(), pageParam.getSize(), list );
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
