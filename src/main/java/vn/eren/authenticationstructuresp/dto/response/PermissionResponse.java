package vn.eren.authenticationstructuresp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse extends BaseResponse {

    Long id;

    String name;

    String description;

    Integer sequence;
}
