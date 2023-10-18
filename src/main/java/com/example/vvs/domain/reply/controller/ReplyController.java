package com.example.vvs.domain.reply.controller;


import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.reply.dto.ReplyRequestDTO;
import com.example.vvs.domain.reply.dto.ReplyResponseDTO;
import com.example.vvs.domain.reply.service.ReplyService;
import com.example.vvs.domain.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/board/{board-id}/reply")
    public ResponseEntity<List<ReplyResponseDTO>> getReplyList(@PathVariable("board-id") Long boardId) {
        return replyService.findReplyList(boardId);
    }

    @PostMapping("/board/{board-id}/reply")
    public ResponseEntity<MessageDTO> postReply(@PathVariable("board-id") Long boardId,
                                                @RequestBody ReplyRequestDTO replyRequestDTO,
                                                @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return replyService.createReply(replyRequestDTO, boardId, memberDetails.getMember().getId());
    }

    @PatchMapping("/board/{board-id}/reply/{reply-id}")
    public ResponseEntity<ReplyResponseDTO> patchReply(@PathVariable("board-id") Long boardId,
                                                       @PathVariable("reply-id") Long replyId,
                                                       @RequestBody ReplyRequestDTO replyRequestDTO,
                                                       @AuthenticationPrincipal MemberDetailsImpl memberDetails) {

        return replyService.updateReply(boardId, replyId, replyRequestDTO, memberDetails.getMember().getId());
    }

    @DeleteMapping("/board/{board-id}/reply/{reply-id}")
    public ResponseEntity<MessageDTO> deleteReply(@PathVariable("board-id") Long boardId,
                                                  @PathVariable("reply-id") Long replyId,
                                                  @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return replyService.deleteReply(boardId, replyId, memberDetails.getMember().getId());
    }
}
