package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.common.Result;
import org.example.tieba.dto.BoardMemberResponse;
import org.example.tieba.dto.CreateBoardRequest;
import org.example.tieba.dto.BoardResponse;
import org.example.tieba.service.BoardMemberService;
import org.example.tieba.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;
    private final BoardMemberService boardMemberService;

    public BoardController(BoardService boardService, BoardMemberService boardMemberService) {
        this.boardService = boardService;
        this.boardMemberService = boardMemberService;
    }

    @PostMapping("")
    public Result<BoardResponse> create(@Valid @RequestBody CreateBoardRequest req) {
        return Result.success(boardService.create(req));
    }

    @GetMapping("/{id}/members")
    public Result<List<BoardMemberResponse>> listMembers(@PathVariable Long id) {
        return Result.success(boardMemberService.listMembers(id));
    }

    @PostMapping("/{id}/members")
    public Result<Void> join(@PathVariable Long id) {
        boardMemberService.join(id, "member");
        return Result.success("加入成功", null);
    }

    @DeleteMapping("/{id}/members")
    public Result<Void> leave(@PathVariable Long id) {
        boardMemberService.leave(id);
        return Result.success("退出成功", null);
    }

    @GetMapping("")
    public Result<List<BoardResponse>> listBoards() {
        return Result.success(boardService.listBoards());
    }
}
