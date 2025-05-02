package vn.eren.authenticationstructuresp.service.impl;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.eren.authenticationstructuresp.common.exception.AppException;
import vn.eren.authenticationstructuresp.common.exception.ErrorCode;
import vn.eren.authenticationstructuresp.config.properties.SecurityProperties;
import vn.eren.authenticationstructuresp.dto.request.AuthenticationRequest;
import vn.eren.authenticationstructuresp.dto.request.IntrospectRequest;
import vn.eren.authenticationstructuresp.dto.request.LogoutRequest;
import vn.eren.authenticationstructuresp.dto.request.RefreshTokenRequest;
import vn.eren.authenticationstructuresp.dto.response.AuthenticationResponse;
import vn.eren.authenticationstructuresp.dto.response.IntrospectResponse;
import vn.eren.authenticationstructuresp.entities.InvalidatedToken;
import vn.eren.authenticationstructuresp.entities.Users;
import vn.eren.authenticationstructuresp.repository.InvalidatedTokenRepository;
import vn.eren.authenticationstructuresp.repository.UsersRepository;
import vn.eren.authenticationstructuresp.service.AuthenticationService;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UsersRepository usersRepository;

    SecurityProperties securityProperties;

    InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Users users = usersRepository.findByUsernameNoCache(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated =  passwordEncoder.matches(request.getPassword(), users.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);
        String token  = generateToken(users);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public String logout(LogoutRequest request) {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);
            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return "Đăng xuất thành công";
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);
            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);

            String username = signedJWT.getJWTClaimsSet().getSubject();
            Users users = usersRepository.findByUsernameNoCache(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            String token  = generateToken(users);
            return AuthenticationResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) {
        try {
            JWSVerifier verifier = new MACVerifier(securityProperties.getSignerKey().getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expTime = (isRefresh)
                    ? signedJWT.getJWTClaimsSet().getExpirationTime()
                    : Date.from(signedJWT.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(securityProperties.getRefreshTokenTime(), ChronoUnit.SECONDS));
            boolean verify = signedJWT.verify(verifier);
            if (!(verify && expTime.after(new Date()))) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            return signedJWT;
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken(Users users) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getUsername())
                .issuer(securityProperties.getIssuer())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(securityProperties.getExpirationTokenTime(), ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(users))
                .claim("account_id", users.getId())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(securityProperties.getSignerKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Users users) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(users.getRoles())) {
            users.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }

            });
        }
        return stringJoiner.toString();
    }
}
