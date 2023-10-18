package com.example.vvs.domain.board.controller;

import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.board.dto.BoardResponseDTO;
import com.example.vvs.domain.board.service.BoardService;
import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService; // 이거 왜 final로 만들지? RequiredArgsConstructor가 인식해서 의존성 주입해줄 수 있도록!

    // 게시글 등록
    @PostMapping("/board")
    public ResponseEntity<MessageDTO> postBoard(@RequestPart(value = "boardRequestDTO") BoardRequestDTO boardRequestDTO,
                                @RequestPart(value = "img", required = false) List<MultipartFile> multipartFileList,
                                @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        System.out.println("Call Controller createBoard Method");
        return boardService.createBoard(boardRequestDTO, multipartFileList, memberDetails.getMember().getId());
    }

    // 게시판 전체 조회
    @GetMapping("/board") // /board?page=1&size=10
    public ResponseEntity<Page<BoardResponseDTO>> getBoardPage(@RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "size", required = false) Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardService.findAllBoard(pageRequest);
    }

    // 게시글 상세 조회
    @GetMapping("/board/{board-id}")
    public ResponseEntity<BoardResponseDTO> getBoard(@PathVariable("board-id") Long id) {
        return boardService.findBoard(id);
    }

    // 게시글 수정
    @PatchMapping("/board/{board-id}")
    public ResponseEntity<MessageDTO> patchBoard(@RequestBody BoardRequestDTO boardRequestDTO,
                                                 @PathVariable("board-id") Long id,
                                                 @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return boardService.updateBoard(boardRequestDTO, id, memberDetails.getMember().getId());
    }

    // 게시글 삭제
    @DeleteMapping("/board/{board-id}")
    public ResponseEntity<MessageDTO> deleteBoard(@PathVariable("board-id") Long id,
                                                  @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return boardService.deleteBoard(id, memberDetails.getMember().getId());
    }


}
