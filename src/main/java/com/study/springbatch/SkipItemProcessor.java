package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class SkipItemProcessor implements ItemProcessor<String, String> {

    private int cnt;

    @Override
    public String process(String item) throws Exception {
        if (item.equals("6") | item.equals("7")) {
            log.info("ItemProcess : {}", item);
            throw new SkippableException("Process failed cnt : " + cnt);
        } else {
            log.info("ItemProcess : {}", item);
            return String.valueOf(Integer.valueOf(item) * -1);
        }
    }
}
