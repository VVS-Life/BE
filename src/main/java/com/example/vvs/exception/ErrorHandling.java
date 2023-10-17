package com.example.vvs.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorHandling {

    NO_UNIQUE_LOGIN_ID(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 ID입니다"),
    NO_UNIQUE_PHONENUMBER(HttpStatus.BAD_REQUEST.value(), "이미 등록되어 있는 번호입니다"),
    IS_EMPTY_LIST(HttpStatus.BAD_REQUEST.value(), "해당 상품 리스트가 없습니다"),
    IS_NULL(HttpStatus.BAD_REQUEST.value(), "해당 상품이 없습니다"),
    NO_UNIQUE_ADMIN_ID(HttpStatus.BAD_REQUEST.value(), "입력하신 ID는 이미 존재하는 ID입니다"),
    NOT_FOUND_ADMIN_ID(HttpStatus.BAD_REQUEST.value(), "해당 ID는 존재하지 않습니다"),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다"),
    NOT_MATCH_USER(HttpStatus.BAD_REQUEST.value(), "일치하는 사용자가 없습니다"),
    EMPTY_SUBSCRIPTION(HttpStatus.BAD_REQUEST.value(), "가입내역이 없습니다"),
    NOT_MATCH_AUTHORIZTION(HttpStatus.BAD_REQUEST.value(), "관리자 권한이 아닙니다"),
    FILE_CONVERSION_FAILURE(HttpStatus.BAD_REQUEST.value(), "파일 변환에 실패했습니다"),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "파일을 찾을 수 없습니다"),
    INVALID_ADMIN_TOKEN(HttpStatus.FORBIDDEN.value(), "유효하지 않은 ADMIN 토큰입니다"),
    NOT_EXISTENCE_ROLE(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 권한입니다"),
    NO_UNIQUE_NICKNAME(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 ID입니다");

    private final int statusCode;
    private final String message;
}
