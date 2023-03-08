package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkListenerConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Integer, String>chunk(5)
                .reader(reader())
                .listener(new CustomItemReaderListener())
                .processor((ItemProcessor<Integer, String>) item -> "item" + item)
                .listener(new CustomItemProcessorListener())
                .writer(items -> items.forEach(item -> log.info("item = {}", item)))
                .listener(new CustomItemWriterListener())
                .listener(new CustomChunkListener())
                .build();
    }

    @Bean
    public ListItemReader<Integer> reader() {
        return new ListItemReader(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

}
