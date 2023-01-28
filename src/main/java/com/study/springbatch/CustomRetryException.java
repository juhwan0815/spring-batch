package com.study.springbatch;

public class CustomRetryException extends RuntimeException {

    public CustomRetryException(String message) {
        super(message);
    }
}
