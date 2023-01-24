package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
public class FlatFileDelimitedConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(3)
                .reader(customItemReader())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ItemWriter<Customer> customItemWriter() {
        return new FlatFileItemWriterBuilder<Customer>()
                .name("flatFileWriter")
                .resource(new FileSystemResource("/Users/juhwan/study/spring-batch/src/main/resources/customer.txt"))
                .append(true)
                .delimited()
                .delimiter("|")
                .names(new String[]{"id", "name", "age"})
                .build();
    }


    @Bean
    public ItemReader<Customer> customItemReader() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "황주환", 27),
                new Customer(2L, "강태환", 27),
                new Customer(3L, "황철원", 27)
        );
        return new ListItemReader<>(customers);
    }

}
