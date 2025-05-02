package vn.eren.authenticationstructuresp.service;

import vn.eren.authenticationstructuresp.dto.request.AuthenticationRequest;
import vn.eren.authenticationstructuresp.dto.request.IntrospectRequest;
import vn.eren.authenticationstructuresp.dto.request.LogoutRequest;
import vn.eren.authenticationstructuresp.dto.request.RefreshTokenRequest;
import vn.eren.authenticationstructuresp.dto.response.AuthenticationResponse;
import vn.eren.authenticationstructuresp.dto.response.IntrospectResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest token);

    String logout(LogoutRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);

}
