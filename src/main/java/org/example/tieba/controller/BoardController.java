package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.dto.ApiResponse;
import org.example.tieba.dto.BoardMemberResponse;
import org.example.tieba.dto.CreateBoardRequest;
import org.example.tieba.dto.BoardResponse;
import org.example.tieba.service.BoardMemberService;
import org.example.tieba.service.BoardService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BoardResponse> create(@Valid @RequestBody CreateBoardRequest req) {
        return ResponseEntity.ok(boardService.create(req));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<ApiResponse<List<BoardMemberResponse>>> listMembers(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(boardMemberService.listMembers(id)));
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<ApiResponse<Void>> join(@PathVariable Long id) {
        boardMemberService.join(id, "member");
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{id}/members")
    public ResponseEntity<ApiResponse<Void>> leave(@PathVariable Long id) {
        boardMemberService.leave(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("")
    public ResponseEntity<List<BoardResponse>> listBoards() {
        return ResponseEntity.ok(boardService.listBoards());
    }
}
