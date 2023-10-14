package com.example.vvs.domain.reply.dto;

import lombok.Builder;

import java.sql.Timestamp;

public class ReplyRequestDTO {
    private String content;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public ReplyRequestDTO(String content, Timestamp createdAt, Timestamp modifiedAt) {
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
