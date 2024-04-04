package com.example.demo.global.exception;

public class AccountNotExistException extends RuntimeException{
    public AccountNotExistException(String message) {
        super(message);
    }
}
