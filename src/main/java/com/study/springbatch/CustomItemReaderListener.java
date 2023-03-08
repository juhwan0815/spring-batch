package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class CustomItemReaderListener implements ItemReadListener<Integer> {
    @Override
    public void beforeRead() {
        log.info(">> beforeRead");
    }

    @Override
    public void afterRead(Integer item) {
        log.info(">> afterRead");
    }

    @Override
    public void onReadError(Exception ex) {
        log.info(">> onReadError");
    }
}
