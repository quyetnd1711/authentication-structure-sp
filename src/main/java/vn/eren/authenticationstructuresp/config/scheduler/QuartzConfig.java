package vn.eren.authenticationstructuresp.config.scheduler;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.eren.authenticationstructuresp.config.scheduler.job.RemoveTokenExpired;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail removeTokenExpiredJobDetail() {
        return JobBuilder.newJob(RemoveTokenExpired.class)
                .withIdentity("removeTokenExpired")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger removeTokenExpiredJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(removeTokenExpiredJobDetail())
                .withIdentity("removeTokenExpired")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/12 1/1 * ? *")) // Run once every half day
                .build();
    }

}
