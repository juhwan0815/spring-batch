package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.ItemReader;

@Slf4j
public class CustomItemReaderListener implements ItemReadListener<String> {
    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(String item) {
        log.info("Thread : {}, item = {}", Thread.currentThread().getName(), item);
    }

    @Override
    public void onReadError(Exception ex) {

    }
}
