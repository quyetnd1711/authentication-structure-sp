package vn.eren.authenticationstructuresp.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolesRequest {

    Long id;

    String name;

    String description;

    Set<Long> permissionIds;
}
