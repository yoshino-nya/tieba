package org.example.tieba.service;

import org.example.tieba.constants.ErrorCode;
import org.example.tieba.dto.CommentResponse;
import org.example.tieba.dto.CreateCommentRequest;
import org.example.tieba.exception.BusinessException;
import org.example.tieba.mapper.CommentMapper;
import org.example.tieba.model.Comment;
import org.example.tieba.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {
    private final SecurityUtil securityUtil;
    private final CommentMapper commentMapper;

    public CommentService(SecurityUtil securityUtil, CommentMapper commentMapper) {
        this.securityUtil = securityUtil;
        this.commentMapper = commentMapper;
    }

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
        } else {
            // 该评论为一级评论，rootId 设置成自己
            comment.setRootId(postId);
        }
        comment.setCreatedAt(LocalDateTime.now());
        commentMapper.insert(comment);

        return new CommentResponse(comment);
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
