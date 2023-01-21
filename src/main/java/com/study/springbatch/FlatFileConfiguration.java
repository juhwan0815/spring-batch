package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlatFileConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, Customer>chunk(5)
                .reader(itemReader())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List items) throws Exception {
                        log.info("items = {}", items);
                    }
                })
                .build();
    }

    @Bean
    public ItemReader itemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new FileSystemResource("/Users/juhwan/study/spring-batch/src/main/resources/customer.txt"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper())
                .targetType(Customer.class)
                .linesToSkip(1)
                .fixedLength()
                .strict(false)
                .addColumns(new Range(1, 5))
                .addColumns(new Range(6, 9))
                .addColumns(new Range(10, 11))
                .names("name", "year", "age")
                .build();
    }

}
