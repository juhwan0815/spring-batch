package com.study.springbatch;

public class RetryableException extends RuntimeException {

    public RetryableException(String message) {
        super(message);
    }
}
