package com.example.vvs.domain.reply.controller;


import com.example.vvs.domain.common.MessageDTO;
import com.example.vvs.domain.reply.dto.ReplyRequestDTO;
import com.example.vvs.domain.reply.dto.ReplyResponseDTO;
import com.example.vvs.domain.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/board/{id}/reply")
    public ResponseEntity<List<ReplyResponseDTO>> getReplyList(@PathVariable Long id) {
        return replyService.findReplyList(id);
    }

    @PostMapping("/board/{id}/reply")
    public ResponseEntity<MessageDTO> postReply(@PathVariable Long id,
                                                @RequestBody ReplyRequestDTO replyRequestDTO) {
        return replyService.createReply(replyRequestDTO, id);
    }

    @PatchMapping("/board/{board-id}/reply/{reply-id}")
    public ResponseEntity<ReplyResponseDTO> patchReply(@PathVariable("board-id") Long boardId,
                                                @PathVariable("reply-id") Long replyId,
                                                @RequestBody ReplyRequestDTO replyRequestDTO) {

        return replyService.updateReply(boardId, replyId, replyRequestDTO);
    }

    @DeleteMapping("/board/{board-id}/reply/{reply-id}")
    public ResponseEntity<MessageDTO> deleteReply(@PathVariable("board-id") Long boardId,
                                                  @PathVariable("reply-id") Long replyId) {
        return replyService.deleteReply(boardId, replyId);
    }
}
