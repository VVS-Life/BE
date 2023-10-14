package com.example.vvs.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorHandling {

    NO_UNIQUE_EMAIL(HttpStatus.BAD_REQUEST, "입력하신 이메일은 이미 존재하는 이메일입니다"),
    NO_UNIQUE_PHONENUMBER(HttpStatus.BAD_REQUEST, "입력하신 폰번호는 이미 존재하는 번호입니다"),
    IS_EMPTY_LIST(HttpStatus.BAD_REQUEST, "해당 상품 리스트가 없습니다"),
    IS_NULL(HttpStatus.BAD_REQUEST, "해당 상품이 없습니다");
    NO_UNIQUE_ADMIN_ID(HttpStatus.BAD_REQUEST, "입력하신 ID는 이미 존재하는 ID입니다"),
    NOT_FOUND_ADMIN_ID(HttpStatus.BAD_REQUEST, "해당 ID는 존재하지 않습니다"),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "일치하는 사용자가 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
