package org.example.quartz;

import org.quartz.*;

import java.util.Date;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyJob implements Job {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("MyJob execute:"+new Date());
        JobDataMap jobDetailMap = jobExecutionContext.getJobDetail().getJobDataMap();
        JobDataMap triggerMap = jobExecutionContext.getTrigger().getJobDataMap();
        JobDataMap mergeMap = jobExecutionContext.getMergedJobDataMap();
        System.out.println("jobDetailMap:"+jobDetailMap.getString("job"));
        System.out.println("triggerMap:"+triggerMap.getString("trigger"));
        System.out.println("mergeMap:"+mergeMap.getString("trigger"));
        System.out.println("name:"+name);
        jobDetailMap.put("count",jobDetailMap.getInt("count")+1);
        System.out.println("count:"+jobDetailMap.getInt("count"));
    }
}
