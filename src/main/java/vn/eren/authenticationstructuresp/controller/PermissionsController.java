package vn.eren.authenticationstructuresp.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.PermissionRequest;
import vn.eren.authenticationstructuresp.dto.request.SearchPermissionsRequest;
import vn.eren.authenticationstructuresp.dto.response.ApiResponse;
import vn.eren.authenticationstructuresp.dto.response.PermissionResponse;
import vn.eren.authenticationstructuresp.service.PermissionsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionsController extends BaseController {

    PermissionsService permissionsService;

    @RateLimiter(name = "getListPermissionsRateLimit", fallbackMethod = "rateLimitFallback")
    @CircuitBreaker(name = "getListPermissionsCircuitBreaker", fallbackMethod = "circuitBreakerFallback")
    @PostMapping("/get-list-permissions")
    public ApiResponse<PagingResponse<PermissionResponse>> getListPermissions(@RequestBody SearchPermissionsRequest request) {
        return ApiResponse.<PagingResponse<PermissionResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.getListPermission(request))
                .build();
    }

    @GetMapping("/get-one-permissions-by-id")
    public ApiResponse<PermissionResponse> getOnePermissionsById(@RequestParam Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.getOnePermission(id))
                .build();
    }

    @PostMapping("/create-permissions")
    public ApiResponse<PermissionResponse> createPermissions(@Valid @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.createPermission(request))
                .build();
    }

    @PostMapping("/update-permissions")
    public ApiResponse<PermissionResponse> updatePermissions(@Valid @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.updatePermission(request))
                .build();
    }

    @PostMapping("/delete-permissions")
    public ApiResponse<PermissionResponse> deletePermissions(@RequestBody Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.deletePermission(id))
                .build();
    }
}
