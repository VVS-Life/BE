package com.example.vvs.domain.reply.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyRequestDTO {
    private String content;

    @Builder
    public ReplyRequestDTO(String content) {
        this.content = content;
    }
}
