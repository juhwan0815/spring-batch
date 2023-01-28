package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class CustomItemReaderListener implements ItemReadListener<Integer> {
    @Override
    public void beforeRead() {
        log.info(">> before Read");
    }

    @Override
    public void afterRead(Integer item) {
        log.info(">> after Read");
    }

    @Override
    public void onReadError(Exception ex) {
        log.info(">> on Read Error");
    }
}
