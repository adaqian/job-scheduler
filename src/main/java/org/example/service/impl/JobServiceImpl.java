package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.JobInfo;
import org.example.service.JobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
@Slf4j
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private Scheduler scheduler;

    public void createJob(JobInfo jobInfo)throws ClassNotFoundException, SchedulerException{
        Trigger trigger = null;
        if (jobInfo.getInterval() ==  null) { //one time
            trigger = TriggerBuilder.newTrigger().withIdentity(jobInfo.getJobName())
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
//                .startAt(quartzBean.getStartTime())
                    .build();
        }else{
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobInfo.getJobName())
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(jobInfo.getInterval()))
//                .startAt(jobInfo.getStartTime())
//                .endAt(jobInfo.getEndTime())
                    .build();
        }
        Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(jobInfo.getJobClass());
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobInfo.getJobName())
                .withDescription(jobInfo.getDescription())
                .storeDurably().build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

//    public void createScheduleJobCron(JobInfo jobInfo) throws ClassNotFoundException, SchedulerException{
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());
//        CronTrigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity(jobInfo.getJobName()).withSchedule(scheduleBuilder).build();
//        createJob(jobInfo.getJobName(),jobInfo.getGroupName(),jobInfo.getJobClass(),trigger);
//    }

    public JobInfo getJob(String jobName){
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            if(!scheduler.checkExists(jobKey)){
                return null;
            }
        } catch (SchedulerException e) {
            log.error("checkExists error!",e);
            return null;
        }
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobName(jobName);
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail != null){
                jobInfo.setJobClass(jobDetail.getJobClass().getName());
                jobInfo.setDescription(jobDetail.getDescription());
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobInfo;
    }

    @Override
    public void pauseJob(String jobName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName));
        } catch (SchedulerException e) {
            log.error("pauseJob failed!", e);
        }
    }

    @Override
    public void resumeJob(String jobName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName));
        } catch (SchedulerException e) {
            log.error("resumeJob failed!", e);
        }
    }

//    public void runOnce(String jobName) {
//        try {
//            scheduler.triggerJob(JobKey.jobKey(jobName));
//        } catch (SchedulerException e) {
//            log.error("run once failed", e);
//        }
//    }

    @Override
    public void updateJob(JobInfo jobInfo) {
        try {
            JobKey jobKey = JobKey.jobKey(jobInfo.getJobName());
            if(!scheduler.checkExists(jobKey)){
                return;
            }
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail != null){
                jobInfo.setJobClass(jobDetail.getJobClass().getName());
                jobInfo.setDescription(jobDetail.getDescription());
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName());
            SimpleScheduleBuilder simpleScheduleBuilder = null;
            Trigger trigger = null;
            if (jobInfo.getInterval() ==  null) { //one time
                simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(simpleScheduleBuilder)
                        .build();
            } else { //repetitive
                simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(jobInfo.getInterval());
                TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(simpleScheduleBuilder)
                        .build();
            }
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            log.error("update job failed:{}",jobInfo.getJobName(), e);
        }
    }

    @Override
    public void deleteJob(String jobName){
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobName));
            scheduler.deleteJob(JobKey.jobKey(jobName));
        } catch (SchedulerException e) {
            log.error("deleteJob failed:{}",jobName, e);
        }

    }

    public void startAllJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            log.error("startAllJobs failed", e);
        }
    }

    public void pauseAllJobs() {
        try {
            scheduler.pauseAll();
        } catch (Exception e) {
            log.error("pauseAllJobs failed", e);
        }
    }

    public void resumeAllJobs() {
        try {
            scheduler.resumeAll();
        } catch (Exception e) {
            log.error("resumeAllJobs failed", e);
        }
    }
}
