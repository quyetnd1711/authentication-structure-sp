package vn.eren.authenticationstructuresp.config.scheduler;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.eren.authenticationstructuresp.config.scheduler.job.RemoveTokenExpired;

@Configuration
public class QuartzConfig {

    private static final String REMOVE_TOKEN_JOB_IDENTITY = "removeTokenExpired";
    private static final String CRON_EXPRESSION_REMOVE_TOKEN = "0 0 0/12 1/1 * ? *"; // Run once every half day

    @Bean
    public JobDetail removeTokenExpiredJobDetail() {
        return JobBuilder.newJob(RemoveTokenExpired.class)
                .withIdentity(REMOVE_TOKEN_JOB_IDENTITY)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger removeTokenExpiredJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(removeTokenExpiredJobDetail())
                .withIdentity(REMOVE_TOKEN_JOB_IDENTITY + "Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_EXPRESSION_REMOVE_TOKEN)) // Run once every half day
                .build();
    }

}
