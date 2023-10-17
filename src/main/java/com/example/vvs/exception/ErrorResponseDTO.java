package com.example.vvs.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorResponseDTO {

    private String errorMessage;
    private int statusCode;

    @Builder
    public ErrorResponseDTO(String errorMessage, int statusCode) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public static ResponseEntity<ErrorResponseDTO> toResponseEntity(ErrorHandling errorHandling) {
        return ResponseEntity
                .status(errorHandling.getStatusCode())
                .body(ErrorResponseDTO.builder()
                        .errorMessage(errorHandling.getMessage())
                        .statusCode(errorHandling.getStatusCode())
                        .build());
    }
}
