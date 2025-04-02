package vn.eren.authenticationstructuresp.config.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(
        prefix = "cache.redis",
        ignoreUnknownFields = false
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CacheProperties {

    String[] server;

    Long expiration;

    Boolean cluster;

    Integer connectionPoolSize;

    Integer connectionMinimumIdleSize;

    Integer subscriptionConnectionPoolSize;

    Integer subscriptionConnectionMinimumIdleSize;
}
