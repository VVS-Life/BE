package com.example.vvs.domain.board.controller;

import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.board.dto.BoardResponseDTO;
import com.example.vvs.domain.board.entity.Board;
import com.example.vvs.domain.board.repository.BoardRepository;
import com.example.vvs.domain.board.service.BoardService;
import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.dto.MemberRequestDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService; // 이거 왜 final로 만들지? RequiredArgsConstructor가 인식해서 의존성 주입해줄 수 있도록!
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    @GetMapping("/board") // /board?page=1&size=10
    public Page<BoardResponseDTO> getBoard(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardService.getBoard(pageRequest);
    }


    // member-id 임시. 나중에 헤더에서 가져와야함.
    @PostMapping("/board/{member-id}")
    public MessageDTO postBoard(@RequestBody BoardRequestDTO boardRequestDTO, @PathVariable("member-id") Long memberId ) {
        return boardService.createBoard(boardRequestDTO, memberId);
    }







}
