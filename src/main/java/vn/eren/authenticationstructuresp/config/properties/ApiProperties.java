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
        prefix = "api",
        ignoreUnknownFields = false
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiProperties {

    @Value("${api.endpoint}")
    String apiEndpoint;

}
