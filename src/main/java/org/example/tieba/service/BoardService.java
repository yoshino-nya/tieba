package org.example.tieba.service;

import org.example.tieba.constants.ErrorCodeConstants;
import org.example.tieba.dto.CreateBoardRequest;
import org.example.tieba.dto.BoardResponse;
import org.example.tieba.exception.BusinessException;
import org.example.tieba.mapper.BoardMapper;
import org.example.tieba.model.Board;
import org.example.tieba.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {
    private final SecurityUtil securityUtil;
    private final BoardMapper boardMapper;

    public BoardService(SecurityUtil securityUtil, BoardMapper boardMapper) {
        this.securityUtil = securityUtil;
        this.boardMapper = boardMapper;
    }

    public BoardResponse create(CreateBoardRequest req) {
        if (boardMapper.existsByName(req.getName())) {
            throw new BusinessException(ErrorCodeConstants.RESOURCE_ALREADY_EXISTS, "贴吧名称已被使用");
        }
        Board board = new Board();
        board.setName(req.getName());
        board.setDescription(req.getDescription());
        board.setManager_id(securityUtil.getCurrentUserId());
        board.setCreated_at(LocalDateTime.now());

        boardMapper.insert(board);
        return new BoardResponse(
                board.getId(), board.getName() + "吧", board.getDescription(), board.getManager_id(), board.getCreated_at()
        );
    }

    public List<BoardResponse> listBoards() {
        return boardMapper.selectAll();
    }
}
