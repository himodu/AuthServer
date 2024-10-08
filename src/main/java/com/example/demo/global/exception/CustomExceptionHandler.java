package com.example.demo.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AccountDuplicationException.class)
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    public ErrorMessage AccountDuplicationException(AccountDuplicationException ex){
        return ErrorMessage.builder()
                .status(HttpStatus.MULTI_STATUS.value())
                .message(ex.getMessage())
                .timeStamp(new Date())
                .build();
    }

    @ExceptionHandler(AccountNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage AccountNotException(AccountNotExistException ex){
        return ErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timeStamp(new Date())
                .build();
    }

    @ExceptionHandler(TokenNullException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage TokenNullException(TokenNullException ex){
        return ErrorMessage.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .timeStamp(new Date())
                .build();
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage TokenExpiredException(TokenExpiredException ex){
        return ErrorMessage.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .timeStamp(new Date())
                .build();
    }
}
