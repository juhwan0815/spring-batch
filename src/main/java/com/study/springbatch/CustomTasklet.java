package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class CustomTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String jobName = chunkContext.getStepContext().getJobName();
        String stepName = contribution.getStepExecution().getStepName();

        log.info("jobName = {}", jobName);
        log.info("stepName = {}", stepName);
        return RepeatStatus.FINISHED;
    }
}
