package com.xor.SalesAnalysis.exception;

public class EmptyResponseHandler extends RuntimeException{
    public EmptyResponseHandler(String message) {
        super(message);
    }
}
