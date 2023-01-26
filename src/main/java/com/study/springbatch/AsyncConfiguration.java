package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AsyncConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() throws InterruptedException {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .listener(new StopWatchJobListener())
                .build();
    }

    @Bean
    public Step step() throws InterruptedException {
        return stepBuilderFactory.get("step")
                .<Customer, Customer>chunk(5)
                .reader(customItemReader())
                .processor(customItemProcessor())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public Step asyncStep() throws InterruptedException {
        return stepBuilderFactory.get("step")
                .<Customer, Future<Customer>>chunk(5)
                .reader(customItemReader())
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter())
                .build();
    }

    @Bean
    public ItemWriter<Customer> customItemWriter() {
        return items -> {
            items.forEach(item -> log.info("item = {}", item));
        };
    }


    @Bean
    public ItemReader<Customer> customItemReader() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "hwang", 27),
                new Customer(2L, "kang", 27),
                new Customer(3L, "chuel", 27),
                new Customer(4L, "chuel", 27),
                new Customer(5L, "chuel", 27),
                new Customer(6L, "chuel", 27),
                new Customer(7L, "chuel", 27),
                new Customer(8L, "chuel", 27),
                new Customer(9L, "chuel", 27),
                new Customer(10L, "chuel", 27),
                new Customer(11L, "chuel", 27),
                new Customer(12L, "chuel", 27),
                new Customer(12L, "chuel", 27),
                new Customer(12L, "chuel", 27),
                new Customer(12L, "chuel", 27)
        );
        return new ListItemReader<>(customers);
    }

    @Bean
    public ItemProcessor<Customer, Customer> customItemProcessor() throws InterruptedException {
        return customer -> {
            Thread.sleep(30);
            log.info("username = {}", customer.getUsername());
            customer.setUsername(customer.getUsername().toUpperCase());
            return customer;
        };
    }

    @Bean
    public ItemProcessor<Customer, Future<Customer>> asyncItemProcessor() throws InterruptedException {
        AsyncItemProcessor<Customer, Customer> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(customItemProcessor());
        asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemWriter asyncItemWriter() throws InterruptedException {
        AsyncItemWriter<Customer> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(customItemWriter());
        return asyncItemWriter;
    }
}
