package org.example.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author: ada
 * @date: 2022/06/14
 **/
@Slf4j
@Component
public class MyJobListener extends JobListenerSupport {
    @Override
    public String getName() {
        return "MyJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobKey = context.getJobDetail().getKey().toString();
        log.debug("job:{} start!",jobKey);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobKey = context.getJobDetail().getKey().toString();
        log.debug("job:{} finished!",jobKey);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        super.jobExecutionVetoed(context);
    }
}
