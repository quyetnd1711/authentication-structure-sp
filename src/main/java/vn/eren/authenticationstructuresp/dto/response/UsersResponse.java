package vn.eren.authenticationstructuresp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eren.authenticationstructuresp.entities.Roles;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersResponse extends BaseResponse{

    String id;

    String username;

    String phoneNumber;

    String email;

    String name;

    String isOtp;

    Boolean isAdmin;

    String isLocked;

    String timeLockedEnd;

    Set<Roles> roles;
}
