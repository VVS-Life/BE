package com.example.vvs.domain.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageDTO {

    private String message;
    private HttpStatus httpStatus;

    @Builder
    public MessageDTO(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
