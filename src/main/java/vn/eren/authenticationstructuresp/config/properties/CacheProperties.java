package vn.eren.authenticationstructuresp.config.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(
        prefix = "cache",
        ignoreUnknownFields = false
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CacheProperties {

    @Value("${cache.redis.server}")
    String[] server;

    @Value("${cache.redis.expiration}")
    Long expiration;

    @Value("${cache.redis.cluster}")
    Boolean cluster;

    @Value("${cache.redis.connection-pool-size}")
    Integer connectionPoolSize;

    @Value("${cache.redis.connection-minimum-idle-size}")
    Integer connectionMinimumIdleSize;

    @Value("${cache.redis.subscription-connection-pool-size}")
    Integer subscriptionConnectionPoolSize;

    @Value("${cache.redis.subscription-connection-minimum-idle-size}")
    Integer subscriptionConnectionMinimumIdleSize;
}
