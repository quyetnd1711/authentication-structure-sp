package vn.eren.authenticationstructuresp.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.eren.authenticationstructuresp.dto.request.AuthenticationRequest;
import vn.eren.authenticationstructuresp.dto.request.IntrospectRequest;
import vn.eren.authenticationstructuresp.dto.request.LogoutRequest;
import vn.eren.authenticationstructuresp.dto.request.RefreshTokenRequest;
import vn.eren.authenticationstructuresp.dto.response.ApiResponse;
import vn.eren.authenticationstructuresp.dto.response.AuthenticationResponse;
import vn.eren.authenticationstructuresp.dto.response.IntrospectResponse;
import vn.eren.authenticationstructuresp.service.AuthenticationService;


@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(authenticationService.authenticate(request))
                .message("Login successfully")
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authentication(@RequestBody IntrospectRequest request) {
        return ApiResponse.<IntrospectResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> authentication(@RequestBody LogoutRequest request) {
        return ApiResponse.<String>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(authenticationService.logout(request))
                .message("Logout successfully")
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(authenticationService.refreshToken(request))
                .build();
    }
}
