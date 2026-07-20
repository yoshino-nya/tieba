package org.example.tieba.board;

import org.example.tieba.common.ErrorCode;
import org.example.tieba.common.BusinessException;
import org.example.tieba.infra.security.SecurityUtil;
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
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "您已经是该吧的成员");
        }
    }

    public void leave(Long boardId) {
        boardMemberMapper.remove(boardId, securityUtil.getCurrentUserId());
    }

    public List<BoardMemberResponse> listMembers(Long boardId) {
        return boardMemberMapper.selectMembers(boardId);
    }
}
