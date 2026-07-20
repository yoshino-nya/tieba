package org.example.tieba.board;

import jakarta.validation.Valid;
import org.example.tieba.common.PageResult;
import org.example.tieba.common.Result;
import org.example.tieba.common.dto.PageParam;
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

    @GetMapping("/{id}")
    public Result<BoardDetailResponse> getDetail(@PathVariable Long id) {
        return Result.success(boardService.getDetail(id));
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
    public Result<PageResult<BoardResponse>> listBoards(@Valid PageParam pageParam) {
        return Result.success(boardService.listBoards(pageParam));
    }
}
