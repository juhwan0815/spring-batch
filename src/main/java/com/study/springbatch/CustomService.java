package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomService<T> {

    private int cnt = 0;

    public T customRead() {
        return (T) ("item" + cnt++);
    }

}
