package com.example.blog.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String name, Long id) {
        super(name + " not found with id " + id);
    }

    public BusinessException(String message) {
        super(message);
    }
}
