package vn.eren.authenticationstructuresp.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.RolesRequest;
import vn.eren.authenticationstructuresp.dto.request.SearchRolesRequest;
import vn.eren.authenticationstructuresp.dto.response.ApiResponse;
import vn.eren.authenticationstructuresp.dto.response.RolesResponse;
import vn.eren.authenticationstructuresp.service.RolesService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolesController extends BaseController {

    RolesService rolesService;

    @PostMapping("/get-list-roles")
    public ApiResponse<PagingResponse<RolesResponse>> getListPermissions(@RequestBody SearchRolesRequest request) {
        return ApiResponse.<PagingResponse<RolesResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(rolesService.getListRole(request))
                .build();
    }

    @GetMapping("/get-one-roles-by-id")
    public ApiResponse<RolesResponse> getOnePermissionsById(@RequestParam Long id) {
        return ApiResponse.<RolesResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(rolesService.getOneRole(id))
                .build();
    }

    @PostMapping("/create-roles")
    public ApiResponse<RolesResponse> createPermissions(@Valid @RequestBody RolesRequest request) {
        return ApiResponse.<RolesResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(rolesService.createRole(request))
                .build();
    }

    @PostMapping("/update-roles")
    public ApiResponse<RolesResponse> updatePermissions(@Valid @RequestBody RolesRequest request) {
        return ApiResponse.<RolesResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(rolesService.updateRole(request))
                .build();
    }

    @PostMapping("/delete-roles")
    public ApiResponse<RolesResponse> deletePermissions(@RequestBody Long id) {
        return ApiResponse.<RolesResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(rolesService.deleteRole(id))
                .build();
    }
}
