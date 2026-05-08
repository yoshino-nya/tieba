package org.example.tieba.service;

import org.example.tieba.constants.ErrorCodeConstants;
import org.example.tieba.dto.BoardMemberResponse;
import org.example.tieba.exception.BusinessException;
import org.example.tieba.mapper.BoardMemberMapper;
import org.example.tieba.model.BoardMember;
import org.example.tieba.util.SecurityUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardMemberService {
    private final SecurityUtil securityUtil;
    private final BoardMemberMapper boardMemberMapper;

    public BoardMemberService(SecurityUtil securityUtil, BoardMemberMapper boardMemberMapper) {
        this.securityUtil = securityUtil;
        this.boardMemberMapper = boardMemberMapper;
    }

    public void join(Long id, String role) {
        BoardMember boardMember = new BoardMember(id, securityUtil.getCurrentUserId(), role, LocalDateTime.now());
        try {
            boardMemberMapper.insert(boardMember);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCodeConstants.RESOURCE_ALREADY_EXISTS, "您已经是该吧的成员");
        }
    }

    public void leave(Long id) {
        boardMemberMapper.remove(id);
    }

    public List<BoardMemberResponse> listMembers(Long boardId) {
        return boardMemberMapper.selectMembers(boardId);
    }
}
