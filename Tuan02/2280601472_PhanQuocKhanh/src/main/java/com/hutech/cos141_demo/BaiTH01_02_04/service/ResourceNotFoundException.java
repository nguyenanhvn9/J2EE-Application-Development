package com.hutech.cos141_demo.BaiTH01_02_04.service;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
} 