package vn.eren.authenticationstructuresp.config.app;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
@FieldDefaults(level = AccessLevel.PROTECTED)
public class AppConfigProperties {

    @Value("${security.signer-key}")
    String signerKey;

    @Value("${security.issuer}")
    String issuer;

    @Value("${security.expiration-token-time}")
    Long expTokenTime;

    @Value("${api.endpoint}")
    String apiEndpoint;

    @Value("${security.refresh-token-time}")
    Long refreshTokenTime;
}
