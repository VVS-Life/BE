package com.example.vvs.domain.reply.dto;

import lombok.Builder;

import java.sql.Timestamp;

public class ReplyResponseDTO {

    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public ReplyResponseDTO(Long id, String content, Timestamp createdAt, Timestamp modifiedAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
