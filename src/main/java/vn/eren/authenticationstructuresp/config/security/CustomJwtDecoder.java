package vn.eren.authenticationstructuresp.config.security;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import vn.eren.authenticationstructuresp.config.properties.SecurityProperties;
import vn.eren.authenticationstructuresp.dto.request.IntrospectRequest;
import vn.eren.authenticationstructuresp.dto.response.IntrospectResponse;
import vn.eren.authenticationstructuresp.service.AuthenticationService;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    private final AuthenticationService authenticationService;

    private final SecurityProperties securityProperties;

    private NimbusJwtDecoder nimbusJwtDecoder;

    public CustomJwtDecoder(AuthenticationService authenticationService,
                            SecurityProperties securityProperties) {
        this.authenticationService = authenticationService;
        this.securityProperties = securityProperties;
    }


    @Override
    public Jwt decode(String token) throws JwtException {
        IntrospectResponse response = authenticationService.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
        if (!response.isValid())
            throw new JwtException("Token invalid");
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(securityProperties.getSignerKey().getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}
