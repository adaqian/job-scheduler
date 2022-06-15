package org.example.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
@Slf4j
//@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("onApplicationEvent");
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger1","group1");
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if(trigger == null){
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                        .startNow()
//                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                        .build();
                JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                        .withIdentity("job1","group1")
                        .build();
                scheduler.scheduleJob(jobDetail,trigger);

            }
            scheduler.start();
            log.info("onApplicationEvent 111");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
