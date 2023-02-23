package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class SkipItemWriter implements ItemWriter<String> {

    private int cnt;

    @Override
    public void write(List<? extends String> items) throws Exception {
        items.forEach(item -> {
            if(item.equals("-12")) {
                throw new SkippableException("ItemWriter failed cnt: " + cnt);
            } else {
                log.info("item = {}", item);
            }
        });
    }

}
