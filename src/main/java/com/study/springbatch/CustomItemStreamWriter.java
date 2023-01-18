package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;

import java.util.List;

@Slf4j
public class CustomItemStreamWriter implements ItemStreamWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        items.forEach(item -> log.info(item));
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        log.info("open");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        log.info("update");
    }

    @Override
    public void close() throws ItemStreamException {
        log.info("close");
    }
}
