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
        prefix = "security",
        ignoreUnknownFields = false
)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SecurityProperties {

    @Value("${security.signer-key}")
    String signerKey;

    @Value("${security.issuer}")
    String issuer;

    @Value("${security.expiration-token-time}")
    Long expTokenTime;

    @Value("${security.refresh-token-time}")
    Long refreshTokenTime;
}
