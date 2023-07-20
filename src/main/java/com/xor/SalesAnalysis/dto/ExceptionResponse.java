package com.xor.SalesAnalysis.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    String message;
    HttpStatus status;
    boolean success;
}
