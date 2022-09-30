package com.server.javaPortfolio.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private HttpStatus errorCode;
    private String errorMessage;
}
