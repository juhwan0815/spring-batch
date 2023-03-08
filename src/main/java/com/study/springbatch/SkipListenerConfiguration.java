package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SkipListenerConfiguration {

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
                .reader(new ItemReader<Integer>() {

                    int i = 0;

                    @Override
                    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;

                        if(i == 3){
                            throw new CustomSkipException("read skipped");
                        }


                        if(i == 10) {
                            return null;
                        }

                        return i;
                    }
                })
                .processor((ItemProcessor<Integer, String>) item -> {

                    if(item == 4) {
                        throw new CustomSkipException("process skipped");
                    }

                    return "item" + item;
                })
                .writer(items -> {
                    for (String item : items) {
                        if(item.equals("item5")) {
                            throw new CustomSkipException("write skipped");
                        }
                        log.info("item = {}", item);
                    }
                })
                .faultTolerant()
                .skip(CustomSkipException.class)
                .skipLimit(3)
                .listener(new CustomSkipListener())
                .build();
    }

}
