package vn.eren.authenticationstructuresp.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersRequest {

    String id;

    String username;

    String password;

    String phoneNumber;

    @Email(message = "INVALID_EMAIL")
    String email;

    String name;

    String isOtp;

    Boolean isAdmin;

    String isLocked;

    String timeLockedEnd;

    Set<Long> roleIds;
}
