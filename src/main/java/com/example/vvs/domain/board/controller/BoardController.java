package com.example.vvs.domain.board.controller;

import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.board.dto.BoardResponseDTO;
import com.example.vvs.domain.board.repository.BoardRepository;
import com.example.vvs.domain.board.service.BoardService;
import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService; // 이거 왜 final로 만들지? RequiredArgsConstructor가 인식해서 의존성 주입해줄 수 있도록!
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @PostMapping("/board/{member-id}")
    public MessageDTO postBoard(@PathVariable("member-id") Long memberId,
                                @RequestPart(value = "boardRequestDTO") BoardRequestDTO boardRequestDTO,
                                @RequestPart(value = "img", required = false) List<MultipartFile> multipartFileList) throws IOException {
        System.out.println("Call Controller createBoard Method");
        return boardService.createBoard(boardRequestDTO, multipartFileList, memberId);
    }

    // 게시판 전체 조회
    @GetMapping("/board") // /board?page=1&size=10
    public Page<BoardResponseDTO> getBoard(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardService.findAllBoard(pageRequest);
    }

    // 게시글 상세 조회
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponseDTO> getBoard(@PathVariable("id") Long id) {
        return boardService.findBoard(id);
    }

    // 게시글 수정
    @PatchMapping("/board/{id}")
    public MessageDTO patchBoard(@RequestBody BoardRequestDTO boardRequestDTO, @PathVariable("id") Long id) {
        return boardService.updateBoard(boardRequestDTO, id);
    }

    // 게시글 삭제
    @DeleteMapping("/board/{id}")
    public MessageDTO deleteBoard(@PathVariable("id") Long id) {
        return boardService.deleteBoard(id);
    }


}
