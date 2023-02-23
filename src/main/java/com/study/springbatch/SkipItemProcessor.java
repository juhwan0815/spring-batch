package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class SkipItemProcessor implements ItemProcessor<String, String> {

    private int cnt;
    @Override
    public String process(String item) throws Exception {

        if(item.equals("6") || item.equals("7")) {
            log.info("ItemProcessor = {}", item);
            throw new SkippableException("ItemProcess failed cnt: " + cnt);
        } else {
            log.info("ItemProcessor = {}", item);
            return String.valueOf(Integer.valueOf(item) * -1);
        }
    }
}
