package vn.eren.authenticationstructuresp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest {

    Long id;

    String name;

    String description;

    String sequence;
}
