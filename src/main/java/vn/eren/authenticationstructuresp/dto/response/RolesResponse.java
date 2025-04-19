package vn.eren.authenticationstructuresp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eren.authenticationstructuresp.entities.Permissions;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolesResponse extends BaseResponse{

    Long id;

    String name;

    String description;

    Set<Permissions> permissions;
}
