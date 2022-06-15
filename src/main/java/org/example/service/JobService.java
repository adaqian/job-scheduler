package org.example.service;

import org.example.pojo.JobInfo;
import org.quartz.SchedulerException;

import java.util.Map;

public interface JobService {
    /**
     * create a Job
     * @param jobInfo
     * @throws ClassNotFoundException
     * @throws SchedulerException
     */
    void createJob(JobInfo jobInfo) throws ClassNotFoundException, SchedulerException;

    /**
     * get job info
     * @param jobName
     * @return
     */
    JobInfo getJob(String jobName);

    /**
     * update job info
     * @param jobInfo
     */
    void updateJob(JobInfo jobInfo);

    /**
     * delete a job
     * @param jobName
     */
    void deleteJob(String jobName);

    /**
     * pause a job
     * @param jobName
     */
    void pauseJob(String jobName);

    /**
     * resume a job
     * @param jobName
     */
    void resumeJob(String jobName);

    /**
     * pause All Jobs
     */
    void pauseAllJobs();

    /**
     *  resume All Jobs
     */
    void resumeAllJobs();

}
