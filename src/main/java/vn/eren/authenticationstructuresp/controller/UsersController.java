package vn.eren.authenticationstructuresp.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.ChangePasswordRequest;
import vn.eren.authenticationstructuresp.dto.request.ResetPasswordRequest;
import vn.eren.authenticationstructuresp.dto.request.SearchUsersRequest;
import vn.eren.authenticationstructuresp.dto.request.UsersRequest;
import vn.eren.authenticationstructuresp.dto.response.ApiResponse;
import vn.eren.authenticationstructuresp.dto.response.UsersResponse;
import vn.eren.authenticationstructuresp.service.UsersService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersController {

    UsersService usersService;

    @PostMapping("/get-list-users")
    public ApiResponse<PagingResponse<UsersResponse>> getListUsers(@RequestBody SearchUsersRequest request) {
        return ApiResponse.<PagingResponse<UsersResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(usersService.getListUser(request))
                .build();
    }

    @GetMapping("/get-one-user-by-id")
    public ApiResponse<UsersResponse> getOneUserById(@RequestParam String id) {
        return ApiResponse.<UsersResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(usersService.getOneUser(id))
                .build();
    }

    @PostMapping("/create-users")
    public ApiResponse<UsersResponse> createUsers(@Valid @RequestBody UsersRequest request) {
        return ApiResponse.<UsersResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(usersService.createUser(request))
                .message("User created successfully")
                .build();
    }

    @PostMapping("/update-users")
    public ApiResponse<UsersResponse> updateUsers(@Valid @RequestBody UsersRequest request) {
        return ApiResponse.<UsersResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(usersService.updateUser(request))
                .message("User updated successfully")
                .build();
    }

    @PostMapping("/delete-users")
    public ApiResponse<UsersResponse> deleteUsers(@RequestBody String id) {
        return ApiResponse.<UsersResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(usersService.deleteUser(id))
                .message("User deleted successfully")
                .build();
    }

    @PostMapping("/change-password-users")
    public ApiResponse<Boolean> changePasswordUsers(@RequestBody ChangePasswordRequest request) {
        return ApiResponse.<Boolean>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(usersService.changePassword(request))
                .message("Password changed successfully")
                .build();
    }

    @PostMapping("/reset-password-users")
    public ApiResponse<Boolean> resetPasswordUsers(@RequestBody ResetPasswordRequest request) {
        return ApiResponse.<Boolean>builder()
                .httpStatus(HttpStatus.OK.value())
                .data(usersService.resetPassword(request))
                .message("Password reset successfully")
                .build();
    }
}
