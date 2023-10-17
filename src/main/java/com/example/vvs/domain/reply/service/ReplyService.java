package com.example.vvs.domain.reply.service;

import com.example.vvs.domain.board.entity.Board;
import com.example.vvs.domain.board.repository.BoardRepository;
import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.member.entity.Member;
import com.example.vvs.domain.member.repository.MemberRepository;
import com.example.vvs.domain.reply.dto.ReplyRequestDTO;
import com.example.vvs.domain.reply.dto.ReplyResponseDTO;
import com.example.vvs.domain.reply.entity.Reply;
import com.example.vvs.domain.reply.repository.ReplyRepository;
import com.example.vvs.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.vvs.exception.ErrorHandling.*;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public ResponseEntity<List<ReplyResponseDTO>> findReplyList(Long boardId){
        List<Reply> replyList = replyRepository.findAllByBoardIdOrderByIdDesc(boardId);
        List<ReplyResponseDTO> dtoList = new ArrayList<>();

        if (replyList.isEmpty()) {
            throw new ApiException(IS_EMPTY_REPLY_LIST);
        }

        for (Reply eachReply : replyList) {
            Reply reply = Reply.builder()
                    .id(eachReply.getId())
                    .content(eachReply.getContent())
                    .createdAt(eachReply.getCreatedAt())
                    .modifiedAt(eachReply.getModifiedAt())
                    .member(eachReply.getMember())
                    .board(eachReply.getBoard())
                    .build();

            ReplyResponseDTO replyResponseDTO = ReplyResponseDTO.builder().reply(reply).build();

            dtoList.add(replyResponseDTO);
        }

        return ResponseEntity.ok(dtoList);
    }

    @Transactional
    public ResponseEntity<MessageDTO> createReply(ReplyRequestDTO replyRequestDTO, Long boardId){

        Member member = memberRepository.findById(replyRequestDTO.getMemberId())
                .orElseThrow(
                        ()->new ApiException(NOT_MATCH_USER)
                );
        Board board = boardRepository.findById(boardId)
                .orElseThrow(
                        ()->new ApiException(NOT_FOUND_BOARD_ID)
                );

        Reply reply = Reply.builder()
                .content(replyRequestDTO.getContent())
                .createdAt(replyRequestDTO.getCreatedAt())
                .modifiedAt(replyRequestDTO.getModifiedAt())
                .member(member)
                .board(board)
                .build();

        replyRepository.save(reply);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("답글 등록 완료")
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @Transactional
    public ResponseEntity<ReplyResponseDTO> updateReply(Long boardId, Long replyId, ReplyRequestDTO replyRequestDTO){

        boardRepository.findById(boardId).orElseThrow(
                ()-> new ApiException(NOT_FOUND_BOARD_ID)
        );

        Reply reply = replyRepository.findById(replyId).orElseThrow(
                () -> new ApiException(NOT_FOUND_REPLY)
        );

        reply.update(replyRequestDTO);

        ReplyResponseDTO replyResponseDTO = ReplyResponseDTO.builder().reply(reply).build();

        return ResponseEntity.ok(replyResponseDTO);
    }

    @Transactional
    public ResponseEntity<MessageDTO> deleteReply(Long boardId, Long replyId){

        boardRepository.findById(boardId).orElseThrow(
                ()-> new ApiException(NOT_FOUND_BOARD_ID)
        );

        replyRepository.findById(replyId).orElseThrow(
                () -> new ApiException(NOT_FOUND_REPLY)
        );

        replyRepository.deleteById(replyId);

        return ResponseEntity.ok(MessageDTO.builder()
                .message("답글 삭제 완료")
                .statusCode(OK.value())
                .build());
    }
}


