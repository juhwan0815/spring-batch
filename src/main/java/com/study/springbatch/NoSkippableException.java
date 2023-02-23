package com.study.springbatch;

public class NoSkippableException extends RuntimeException {

    public NoSkippableException(String message) {
        super(message);
    }
}
