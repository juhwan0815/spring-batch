package com.study.springbatch.scheduler;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

public abstract class JobRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        doRun(args);
    }

    protected abstract void doRun(ApplicationArguments args) throws SchedulerException;


    public Trigger buildJobTrigger(String scheduelExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduelExp))
                .build();
    }

    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).withIdentity(name,group)
                .usingJobData(jobDataMap)
                .build();
    }
}
