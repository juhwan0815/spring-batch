package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Integer, String>chunk(5)
                .listener(new CustomChunkListener())
                .reader(new ItemReader<Integer>() {

                    int i = 0;

                    @Override
                    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

                        if(i == 10) {
                            return null;
                        }

                        i++;
                        return i;
                    }
                })
                .listener(new CustomItemReaderListener())
                .processor(new ItemProcessor<Integer, String>() {
                    @Override
                    public String process(Integer item) throws Exception {
                        throw new RuntimeException("failed");
//                        return "item" + item;
                    }
                })
                .listener(new CustomItemProcessListener())
                .writer(items -> items.forEach(item -> log.info("item = {}", item)))
                .listener(new CustomItemWriterListener())
                .build();
    }

}
