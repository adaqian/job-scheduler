package org.example.config;

import org.example.quartz.MyJobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
@Configuration
public class SchedulerConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyJobListener myJobListener;

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/spring-quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public Executor schedulerThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
        return executor;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setSchedulerName("clulster_scheduler");
        factory.setDataSource(dataSource);
        factory.setApplicationContextSchedulerContextKey("application");
        factory.setQuartzProperties(quartzProperties());
        factory.setTaskExecutor(schedulerThreadPool());
        factory.setStartupDelay(10);
        return factory;
    }

    @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        scheduler.getListenerManager().addJobListener(myJobListener);
        return scheduler;
    }
}
