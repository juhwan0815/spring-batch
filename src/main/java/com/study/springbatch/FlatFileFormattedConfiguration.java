package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlatFileFormattedConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int chunkSize = 2;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(chunkSize)
                .reader(customItemReader())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ItemReader<Customer> customItemReader() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "user1", 18),
                new Customer(2L, "user2", 18),
                new Customer(3L, "user3", 18),
                new Customer(4L, "user4", 18),
                new Customer(5L, "user5", 18)
        );

        return new ListItemReader<>(customers);
    }

    @Bean
    public ItemWriter<Customer> customItemWriter() {
        return new FlatFileItemWriterBuilder<Customer>()
                .name("flatFileWriter")
                .resource(new FileSystemResource("/Users/juhwan/study/spring-batch/src/main/resources/customer.txt"))
                .formatted()
                .format("%-2d%-6s%-2d")
                .names(new String[] {"id", "name", "age"})
                .build();
    }

}
