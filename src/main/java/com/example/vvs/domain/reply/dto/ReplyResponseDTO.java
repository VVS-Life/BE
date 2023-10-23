package com.example.vvs.domain.reply.dto;

import com.example.vvs.domain.reply.entity.Reply;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReplyResponseDTO {

    private Long id;
    private Long memberId;
    private Long boardId;
    private String content;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public ReplyResponseDTO(Reply reply) {
        this.id = reply.getId();
        this.memberId = reply.getMember().getId();
        this.boardId = reply.getBoard().getId();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
