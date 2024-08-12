package com.example.demo.global.exception;

import lombok.*;

import java.util.Date;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private int status;
    private Date timeStamp;
    private String message;

}
