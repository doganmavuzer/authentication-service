package com.mavuzer.authentication.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime timeStamp;
    private String pathUri;
}
