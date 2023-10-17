package com.example.vvs.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorResponseDTO {

    private String errorMessage;
    private HttpStatus errorCode;

    @Builder
    public ErrorResponseDTO(String errorMessage, HttpStatus errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public static ResponseEntity<ErrorResponseDTO> toResponseEntity(ErrorHandling errorHandling) {
        return ResponseEntity
                .status(errorHandling.getHttpStatus())
                .body(ErrorResponseDTO.builder()
                        .errorMessage(errorHandling.getMessage())
                        .errorCode(errorHandling.getHttpStatus())
                        .build());
    }
}
