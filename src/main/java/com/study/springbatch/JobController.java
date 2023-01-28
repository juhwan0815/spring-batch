package com.study.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobRegistry jobRegistry;
    private final JobExplorer jobExplorer;

    private final JobOperator jobOperator;

    @PostMapping("/batch/start")
    public String start(@RequestBody JobInfo jobInfo) throws NoSuchJobException, JobInstanceAlreadyExistsException, JobParametersInvalidException {

        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            log.info("jobName : {}", job.getName());
            jobOperator.start(job.getName(), "id=" + jobInfo.getId());
        }

        return "batch is started";
    }

    @PostMapping("/batch/stop")
    public String stop() throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {

        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            log.info("jobName : {}", job.getName());

            Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(job.getName());
            JobExecution jobExecution = runningJobExecutions.iterator().next();
            jobOperator.stop(jobExecution.getJobId());
        }

        return "batch is stopped";
    }

    @PostMapping("/batch/restart")
    public String restart() throws NoSuchJobException, NoSuchJobExecutionException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException {

        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            log.info("jobName : {}", job.getName());

            JobInstance lastJobInstance = jobExplorer.getLastJobInstance(job.getName());
            JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);

            jobOperator.restart(lastJobExecution.getId());
        }

        return "batch is restarted";
    }


}
