package vn.eren.authenticationstructuresp.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import vn.eren.authenticationstructuresp.common.exception.ErrorCode;
import vn.eren.authenticationstructuresp.dto.response.ApiResponse;

public class BaseController {

    public <T> ApiResponse<T> rateLimitFallback(RequestNotPermitted e) {
        return ApiResponse.<T>builder()
                .httpStatus(ErrorCode.TOO_MANY_REQUESTS.getStatusCode().value())
                .message(ErrorCode.TOO_MANY_REQUESTS.getMessage())
                .code(ErrorCode.TOO_MANY_REQUESTS.getCode())
                .build();
    }

    public <T> ApiResponse<T> circuitBreakerFallback(CallNotPermittedException e) {
        return ApiResponse.<T>builder()
                .httpStatus(ErrorCode.SERVICE_UNAVAILABLE.getStatusCode().value())
                .message(ErrorCode.SERVICE_UNAVAILABLE.getMessage())
                .code(ErrorCode.SERVICE_UNAVAILABLE.getCode())
                .build();
    }
}
