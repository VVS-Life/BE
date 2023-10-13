package com.example.vvs.domain.board.service;

import com.example.vvs.domain.board.dto.BoardRequestDTO;
import com.example.vvs.domain.board.dto.BoardResponseDTO;
import com.example.vvs.domain.board.entity.Board;
import com.example.vvs.domain.board.repository.BoardRepository;
import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service // 스프링빈 등록
@RequiredArgsConstructor // 의존성 주입(final에 대해)
@Transactional(readOnly = true) // CRUD에서 R은 대부분의 메소드에 사용하기에 클래스 단위에서 권한을 주고, 메소드 단위에서 CUD 기능이 필요하면 거기서 권한을 주도록.
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    public Page<BoardResponseDTO> getBoard(PageRequest pageRequest) {
        Page<BoardResponseDTO> boards = boardRepository.findAllByOrderByIdDesc(pageRequest)
                .map((Board board) -> BoardResponseDTO.builder().board(board).build());

        return boards;
    }

    @Transactional
    public MessageDTO createBoard(BoardRequestDTO boardRequestDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        NullPointerException::new
                );

        Board board = Board.builder()
                .boardRequestDTO(boardRequestDTO)
                .member(member)
                .build();

        boardRepository.save(board);

        return MessageDTO.builder()
                .message("문의글 등록 성공")
                .statusCode(200)
                .build();
    }
}
