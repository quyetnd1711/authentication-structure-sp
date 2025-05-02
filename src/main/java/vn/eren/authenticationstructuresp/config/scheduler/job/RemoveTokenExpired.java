package vn.eren.authenticationstructuresp.config.scheduler.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vn.eren.authenticationstructuresp.repository.InvalidatedTokenRepository;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoveTokenExpired implements Job {

    private static final Logger logger = LoggerFactory.getLogger(RemoveTokenExpired.class);

    InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("============> Start job remove token expired");
        var lstTokenExpired = invalidatedTokenRepository.findTokenExpired();
        if (!lstTokenExpired.isEmpty()) {
            invalidatedTokenRepository.deleteAll(lstTokenExpired);
        }
        logger.info("============> End job remove token expired");
    }
}
