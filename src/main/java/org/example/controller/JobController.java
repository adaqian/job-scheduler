package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.JobInfo;
import org.example.pojo.RespBean;
import org.example.pojo.RespBeanEnum;
import org.example.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
@Slf4j
@RequestMapping("/api")
@RestController
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * create a job
     * @param jobInfo
     * @return
     */
    @PostMapping("/createJob")
    public RespBean createJob(@RequestBody JobInfo jobInfo) {
        try {
            jobService.createJob(jobInfo);
            return RespBean.success();
        } catch (ClassNotFoundException e) {
            log.error("Job class not found!",e);
            return RespBean.error(RespBeanEnum.CREATE_JOB_ERROR,"Job class not found!");
        } catch (SchedulerException e) {
            log.error("Create job error!",e);
            return RespBean.error(RespBeanEnum.CREATE_JOB_ERROR,e.getMessage());
        }
    }

    /**
     * get job info
     * @param jobName
     * @return
     */
    @GetMapping("/getJob/{jobName}")
    public Object getJob(@PathVariable(name = "jobName") String jobName) {
        JobInfo jobInfo = jobService.getJob(jobName);
        if(jobInfo == null){
            return RespBean.error(RespBeanEnum.JOB_NOT_EXIST);
        }
        return RespBean.success(jobInfo);
    }

    /**
     * update a job
     * @param jobInfo
     * @return
     */
    @PostMapping("/updateJob")
    public Object updateJob(@RequestBody JobInfo jobInfo) {
        jobService.updateJob(jobInfo);
        return RespBean.success();
    }

    /**
     * delete a job
     * @param jobName
     * @return
     */
    @GetMapping("/deleteJob/{jobName}")
    public Object deleteJob(@PathVariable(name = "jobName") String jobName) {
        jobService.deleteJob(jobName);
        return RespBean.success();
    }

    /**
     * pause a job
     * @param jobName
     * @return
     */
    @GetMapping("/pauseJob/{jobName}")
    public RespBean pauseJob(@PathVariable(name = "jobName") String jobName) {
        jobService.pauseJob(jobName);
        return RespBean.success();
    }

    /**
     * resume a job
     * @param jobName
     * @return
     */
    @GetMapping("/resumeJob/{jobName}")
    public Object resumeJob(@PathVariable(name = "jobName") String jobName) {
        jobService.resumeJob(jobName);
        return RespBean.success();
    }

    /**
     * pause all jobs
     * @return
     */
    @GetMapping("/pauseAllJobs")
    public Object pauseAllJobs() {
        jobService.pauseAllJobs();
        return RespBean.success();
    }

    /**
     * resumeAllJobs
     * @return
     */
    @GetMapping("/resumeAllJobs")
    public Object resumeAllJobs() {
        jobService.resumeAllJobs();
        return RespBean.success();
    }
}
