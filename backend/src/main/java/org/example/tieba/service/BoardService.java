package org.example.tieba.service;

import org.example.tieba.constants.ErrorCode;
import org.example.tieba.dto.BoardDetailResponse;
import org.example.tieba.dto.BoardResponse;
import org.example.tieba.dto.CreateBoardRequest;
import org.example.tieba.exception.BusinessException;
import org.example.tieba.mapper.BoardMapper;
import org.example.tieba.model.Board;
import org.example.tieba.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {
    private final SecurityUtil securityUtil;
    private final BoardMapper boardMapper;
    private final BoardMemberService boardMemberService;

    public BoardService(SecurityUtil securityUtil, BoardMapper boardMapper, BoardMemberService boardMemberService) {
        this.securityUtil = securityUtil;
        this.boardMapper = boardMapper;
        this.boardMemberService = boardMemberService;
    }

    @Transactional
    public BoardResponse create(CreateBoardRequest req) {
        if (boardMapper.existsByName(req.getName())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "贴吧名称已被使用");
        }
        Board board = new Board();
        board.setName(req.getName());
        board.setDescription(req.getDescription());
        board.setManager_id(securityUtil.getCurrentUserId());
        board.setCreated_at(LocalDateTime.now());

        boardMapper.insert(board);

        // 创建贴吧后需要把当前用户设置为吧主
        boardMemberService.join(board.getId(), "owner");
        return new BoardResponse(
                board.getId(), board.getName() + "吧", board.getDescription(), board.getManager_id(), board.getCreated_at(), 1, 0
        );
    }

    public BoardDetailResponse getDetail(Long id) {
        return boardMapper.selectDetailById(id);
    }

    public List<BoardResponse> listBoards() {
        return boardMapper.selectAll();
    }
}
