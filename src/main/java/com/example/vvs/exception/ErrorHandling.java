package com.example.vvs.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorHandling {

    NO_UNIQUE_EMAIL(HttpStatus.BAD_REQUEST, "입력하신 이메일은 이미 존재하는 이메일입니다"),
    NO_UNIQUE_PHONENUMBER(HttpStatus.BAD_REQUEST, "입력하신 폰번호는 이미 존재하는 번호입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}