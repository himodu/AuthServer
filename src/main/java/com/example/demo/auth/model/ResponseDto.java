package com.example.demo.auth.model;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private int status;
    private String message;
}
