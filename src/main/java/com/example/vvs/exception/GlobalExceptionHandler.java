package com.example.vvs.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDTO> methodValidException(MethodArgumentNotValidException e) {

        ErrorResponseDTO errorResponseDTO = makeErrorResponse(e.getBindingResult());
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    private ErrorResponseDTO makeErrorResponse(BindingResult bindingResult) {

        String message = "";
        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        return ErrorResponseDTO
                .builder()
                .errorMessage(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ErrorResponseDTO> handleCustomException(ApiException e) {

        log.error("handleDataException throw Exception : {}", e.getErrorHandling());
        return ErrorResponseDTO.toResponseEntity(e.getErrorHandling());
    }
}
