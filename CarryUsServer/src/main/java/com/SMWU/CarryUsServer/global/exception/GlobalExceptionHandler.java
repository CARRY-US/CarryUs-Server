package com.SMWU.CarryUsServer.global.exception;

import com.SMWU.CarryUsServer.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.SMWU.CarryUsServer.global.exception.CommonExceptionType.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({ClientException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(ClientException ex) {
        return ResponseEntity.status(ex.getExceptionType().status()).body(ErrorResponse.of(ex.getExceptionType()));
    }

    @ExceptionHandler({ServerException.class})
    protected ResponseEntity<ErrorResponse> handleServerException(ServerException ex) {
        log.error("🚨BusinessException occurred: {} 🚨", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleServerException(Exception ex) {
        log.error("🚨InternalException occurred: {} 🚨", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(final NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(NOT_FOUND_PATH, ex.getRequestURL()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(INVALID_INPUT_VALUE, ex.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpMediaTypeNotSupportedException ex){
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ErrorResponse.of(INVALID_JSON_TYPE, ex.getMessage()));
    }
}
