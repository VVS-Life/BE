package com.example.vvs.domain.reply.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReplyRequestDTO {
    private String memberId;
    private String content;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public ReplyRequestDTO(String memberId, String content, Timestamp createdAt, Timestamp modifiedAt) {
        this.memberId = memberId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
