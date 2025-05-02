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
        prefix = "security",
        ignoreUnknownFields = false
)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SecurityProperties {

    String signerKey;

    String issuer;

    Long expirationTokenTime;

    Long refreshTokenTime;

    String passwordDefault;
}
