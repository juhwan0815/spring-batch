package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy;
import org.springframework.batch.core.step.skip.NeverSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SkipConfiguration {

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
                .<String, String>chunk(5)
                .reader(new ItemReader<String>() {

                    int i = 0;

                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;

                        if (i == 3) {
                            throw new NoSkippableException("skip");
                        }

                        log.info("ItemReader : {}", i);
                        return i > 20 ? null : String.valueOf(i);
                    }
                })
                .processor(skipItemProcessor())
                .writer(skipItemWriter())
                .faultTolerant()
                .noSkip(NoSkippableException.class)
//                .skipPolicy(new NeverSkipItemSkipPolicy())
                .build();
    }

    @Bean
    public SkipPolicy limitCheckingItemSkipPolicy() {

        Map<Class<? extends Throwable>, Boolean> exceptionClasses = new HashMap<>();
        exceptionClasses.put(SkippableException.class, true);
        exceptionClasses.put(NoSkippableException.class, false);

        return new LimitCheckingItemSkipPolicy(3, exceptionClasses);
    }

    @Bean
    public ItemProcessor<String, String> skipItemProcessor() {
        return new SkipItemProcessor();
    }

    @Bean
    public ItemWriter<String> skipItemWriter() {
        return new SkipItemWriter();
    }

}
