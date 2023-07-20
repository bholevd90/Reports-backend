package com.xor.SalesAnalysis.exception;

import com.xor.SalesAnalysis.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<ExceptionResponse> handleMissingParameterException(MissingServletRequestParameterException e){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setSuccess(false);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmptyResponseHandler.class)
    ResponseEntity<ExceptionResponse> handleEmptyResponseHandler(EmptyResponseHandler e){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setSuccess(false);
        return ResponseEntity.badRequest().body(response);
    }

}
