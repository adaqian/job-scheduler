package org.example.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Thread.sleep(2000);
            log.info("InstanceId={},taskname={}",context.getScheduler().getSchedulerInstanceId(),context.getJobDetail().getKey().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
