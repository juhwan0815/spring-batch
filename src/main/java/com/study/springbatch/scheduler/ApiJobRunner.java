package com.study.springbatch.scheduler;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Component
@RequiredArgsConstructor
public class ApiJobRunner extends JobRunner {

    private final Scheduler scheduler;

    @Override
    protected void doRun(ApplicationArguments args) {
        JobDetail jobDetail = buildJobDetail(ApiSchJob.class, "apiJob", "batch", new HashMap());
        Trigger trigger = buildJobTrigger("0/30 * * * * ?");

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
