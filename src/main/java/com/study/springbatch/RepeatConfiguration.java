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
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RepeatConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int chunkSize = 5;

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
                .<String, String>chunk(chunkSize)
                .reader(new ListItemReader<>(Arrays.asList(
                        "item1", "item2", "item3", "item4", "item5")))
                .processor(new ItemProcessor<String, String>() {
                    RepeatTemplate repeatTemplate = new RepeatTemplate();

                    @Override
                    public String process(String item) throws Exception {
//                        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(chunkSize));
//                        repeatTemplate.setCompletionPolicy(new TimeoutTerminationPolicy(300));

                        CompositeCompletionPolicy completionPolicy = new CompositeCompletionPolicy();
                        completionPolicy.setPolicies(new CompletionPolicy[]{
                                new TimeoutTerminationPolicy(3000),
                                new SimpleCompletionPolicy(chunkSize)
                        });
                        repeatTemplate.setCompletionPolicy(completionPolicy);
                        repeatTemplate.setExceptionHandler(exceptionHandler());
                        repeatTemplate.iterate(new RepeatCallback() {
                            @Override
                            public RepeatStatus doInIteration(RepeatContext context) throws Exception {
                                log.info("repeatTemplate is testing");
//                                throw new RuntimeException("Exception is occurred");
//                                return RepeatStatus.CONTINUABLE;
                                return RepeatStatus.FINISHED;
                            }
                        });

                        return item;
                    }
                })
                .writer(items -> items.forEach(item -> log.info("item = {}", item)))
                .build();
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new SimpleLimitExceptionHandler(3);
    }


}
