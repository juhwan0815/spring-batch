package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

@Slf4j
public class CustomItemWriterListener implements ItemWriteListener<String> {

    @Override
    public void beforeWrite(List<? extends String> items) {

    }

    @Override
    public void afterWrite(List<? extends String> items) {
        log.info("Thread : {}, item.size = {}", Thread.currentThread().getName(), items.size());
    }

    @Override
    public void onWriteError(Exception exception, List<? extends String> items) {

    }
}
