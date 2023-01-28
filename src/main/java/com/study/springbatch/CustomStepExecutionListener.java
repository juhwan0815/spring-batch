package com.study.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("stepName = {}", stepExecution.getStepName());

        stepExecution.getExecutionContext().put("name", "user1");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus exitStatus = stepExecution.getExitStatus();
        log.info("exitStatus = {}", exitStatus);

        BatchStatus status = stepExecution.getStatus();
        log.info("status = {}", status);


        String name = (String) stepExecution.getExecutionContext().get("name");
        log.info("name = {}", name);
        return ExitStatus.COMPLETED;
    }
}
