package com.hutech.cos141_demo.BaiTH124.exception;

public class ApiException extends RuntimeException {
    
    public ApiException(String message) {
        super(message);
    }
    
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
} 