package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class CustomItemWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> customers) throws Exception {
        customers.forEach(customer -> {
            log.info(customer.getName());
        });
    }
}
