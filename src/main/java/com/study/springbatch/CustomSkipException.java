package com.study.springbatch;

public class CustomSkipException extends RuntimeException {

    public CustomSkipException(String message) {
        super(message);
    }
}