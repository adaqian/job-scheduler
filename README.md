# job-scheduler
a job scheduler sample based on springboot and quartz
***
# Features
* Provides rest Api to Create/Read/Update/Delete jobs to run.
* Supports one time execution and repetitive executions triggered at a fixed interval,
  i.e. 10 seconds.
* Jobs are persisted in mysql database.
* The system supports distributed computing and it is scalable to thousands of jobs and many workers.
# API description:
- create a job
http://localhost:8080/api/createJob
{
"jobName":"TestJob01",
"jobClass":"org.example.job.TestJob01",
"interval":10
}
- read a job
http://localhost:8080/api/getJob/TestJob01
- update a job
http://localhost:8080/api/updateJob/
{
"jobName":"TestJob01",
"jobClass":"org.example.job.TestJob01",
"interval":30
}
- delete a job
http://localhost:8080/api/deleteJob/TestJob01
- pause a job
http://localhost:8080/api/pauseJob/TestJob01
- resume a job
http://localhost:8080/api/resumeJob/TestJob01
- pause all jobs
http://localhost:8080/api/pauseAllJobs
- resume all jobs
http://localhost:8080/api/resumeAllJobs
# Pull image
docker pull registry.cn-hangzhou.aliyuncs.com/adaqian/job-scheduler:latest
# Some extra things:
- Add JobListener to monitor job execution,such as timeout detection, retry.
- We can consider integrating redis to improve concurrency and make the interaction faster.