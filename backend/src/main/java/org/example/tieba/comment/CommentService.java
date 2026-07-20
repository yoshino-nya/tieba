package org.example.tieba.comment;

import org.example.tieba.common.ErrorCode;
import org.example.tieba.common.BusinessException;
import org.example.tieba.user.UserMapper;
import org.example.tieba.infra.security.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {
    private final SecurityUtil securityUtil;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    public CommentService(SecurityUtil securityUtil, CommentMapper commentMapper, UserMapper userMapper) {
        this.securityUtil = securityUtil;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    public CommentResponse create(CreateCommentRequest req, Long postId) {
        Comment comment = new Comment();

        comment.setUserId(securityUtil.getCurrentUserId());
        comment.setContent(req.getContent());
        comment.setPostId(postId);
        comment.setStatus((byte) 0);

        // 该评论不是一级评论，检查回复的评论
        if (req.getParentId() != null) {
            Comment parentComment = commentMapper.selectById(req.getParentId());

            // 回复的评论不存在 可能是被删了 或者
            if (parentComment == null)
                throw new BusinessException(ErrorCode.NOT_FOUND, "要回复的评论不存在");

            // 回复的评论与当前评论不属于同一帖子，按理不该出现这种情况
            if (!Objects.equals(parentComment.getPostId(), postId))
                throw new BusinessException(ErrorCode.BAD_REQUEST, "无效的回复");

            comment.setRootId(parentComment.getRootId());
            comment.setParentId(parentComment.getId());
        }
        comment.setCreatedAt(LocalDateTime.now());
        commentMapper.insert(comment);

        // 一级评论需要单独把 rootId 设置为 id
        if (comment.getRootId() == null) {
            comment.setRootId(comment.getId());
            commentMapper.setRootId(comment.getRootId());
        }
        CommentResponse resp = new CommentResponse();
        resp.setId(comment.getId());
        resp.setContent(comment.getContent());
        resp.setPostId(postId);
        resp.setRootId(comment.getRootId());
        resp.setParentId(comment.getParentId());
        resp.setCreatedAt(LocalDateTime.now());
        resp.setUserBrief(userMapper.findBriefById(comment.getUserId()));
        return resp;
    }

    public List<CommentResponse> list(Long postId) {
        return commentMapper.selectByPostId(postId);
    }

    public void delete(Long id) {
        Long userId = commentMapper.selectUserIdById(id);
        if (userId == null)
            throw new BusinessException(ErrorCode.NOT_FOUND, "评论不存在或已被删除");
        if (!Objects.equals(userId, securityUtil.getCurrentUserId()))
            throw new BusinessException(ErrorCode.BAD_REQUEST, "无权限删除此评论");
        commentMapper.deleteById(id);
    }
}
