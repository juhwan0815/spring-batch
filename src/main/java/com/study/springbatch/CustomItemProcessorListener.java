package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomItemProcessorListener implements ItemProcessListener<String, String> {
    @Override
    public void beforeProcess(String item) {

    }

    @Override
    public void afterProcess(String item, String result) {
        log.info("Thread : {}, item = {}", Thread.currentThread().getName(), item);
    }

    @Override
    public void onProcessError(String item, Exception e) {

    }
}
