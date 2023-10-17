package com.example.vvs.domain.reply.dto;

import com.example.vvs.domain.product.entity.Product;
import com.example.vvs.domain.reply.entity.Reply;
import lombok.Builder;

import java.sql.Timestamp;

public class ReplyResponseDTO {

    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public ReplyResponseDTO(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
