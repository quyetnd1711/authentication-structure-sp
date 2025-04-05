package vn.eren.authenticationstructuresp.repository.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.eren.authenticationstructuresp.config.specification.BaseSpecification;
import vn.eren.authenticationstructuresp.entities.Permissions;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PermissionsSpecification extends BaseSpecification<Permissions> {

    public static PermissionsSpecification builder() {
        return new PermissionsSpecification();
    }

}
