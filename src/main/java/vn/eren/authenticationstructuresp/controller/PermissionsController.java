package vn.eren.authenticationstructuresp.controller;

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
public class PermissionsController {

    PermissionsService permissionsService;

    @PostMapping("/get-list-permissions")
    public ApiResponse<PagingResponse<PermissionResponse>> getListUsers(@RequestBody SearchPermissionsRequest request) {
        return ApiResponse.<PagingResponse<PermissionResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.getListPermission(request))
                .build();
    }

    @GetMapping("/get-one-permissions-by-id")
    public ApiResponse<PermissionResponse> getOneUserById(@RequestParam Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.getOnePermission(id))
                .build();
    }

    @PostMapping("/create-permissions")
    public ApiResponse<PermissionResponse> createUsers(@Valid @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.createPermission(request))
                .build();
    }

    @PostMapping("/update-permissions")
    public ApiResponse<PermissionResponse> updateUsers(@Valid @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.updatePermission(request))
                .build();
    }

    @PostMapping("/delete-permissions")
    public ApiResponse<PermissionResponse> deleteUsers(@RequestBody Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(permissionsService.deletePermission(id))
                .build();
    }
}
