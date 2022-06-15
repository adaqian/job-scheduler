package org.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
public class TestJob {
    public static void main(String[] args) {
        int count = 0;
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1","group1")
                .usingJobData("job","jobDetail001")
                .usingJobData("name","jobName001")
                .usingJobData("count",count)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","triggerGroup1")
                .usingJobData("trigger","trigger001")
                .usingJobData("name","triggerName001")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }
}
