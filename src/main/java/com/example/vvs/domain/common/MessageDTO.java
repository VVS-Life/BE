package com.example.vvs.domain.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageDTO {

    private String message;
    private int statusCode;

    @Builder
    public MessageDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
