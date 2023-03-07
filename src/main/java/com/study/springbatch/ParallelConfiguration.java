package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ParallelConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int chunkSize = 5;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(flow1())
                .split(taskExecutor())
                .add(flow2())
                .end()
                .build();
    }

    @Bean
    public Flow flow1() {
        TaskletStep step1 = stepBuilderFactory.get("step1")
                .tasklet(customTasklet())
                .build();
        return new FlowBuilder<Flow>("flow1")
                .start(step1)
                .build();
    }

    @Bean
    public Flow flow2() {

        TaskletStep step2 = stepBuilderFactory.get("step2")
                .tasklet(customTasklet())
                .build();

        TaskletStep step3 = stepBuilderFactory.get("step3")
                .tasklet(customTasklet())
                .build();

        return new FlowBuilder<Flow>("flow2")
                .start(step2)
                .next(step3)
                .build();
    }

//    @Bean
//    public Step step() {
//        return stepBuilderFactory.get("step")
//                .tasklet(customTasklet())
//                .build();
//    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setThreadNamePrefix("async-thread");
        return taskExecutor;
    }

    @Bean
    public Tasklet customTasklet() {
        return new CustomTasklet();
    }

//    @Bean
//    public Step step() {
//        return stepBuilderFactory.get("step")
//                .<String, String>chunk(chunkSize)
//                .reader(customItemReader())
//                .processor(customItemProcessor())
//                .writer(customItemWriter())
//                .listener(new CustomItemWriterListener())
//                .build();
//    }


//    @Bean
//    public ListItemReader<String> customItemReader() {
//        List<String> items = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            items.add(String.valueOf(i));
//        }
//        return new ListItemReader<>(items);
//    }
//
//    @Bean
//    public ItemProcessor<String, String> customItemProcessor() {
//        return item -> {
//            log.info("item = {}", item);
//            return "item" + item;
//        };
//    }
//
//    @Bean
//    public ItemWriter<String> customItemWriter() {
//        return items -> items.forEach(item -> log.info("item = {}", item));
//    }

}