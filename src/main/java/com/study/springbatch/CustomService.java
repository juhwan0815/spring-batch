package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomService<T>{

    public void customWrite(T item) {
        log.info("item = {}", item);
    }

}
