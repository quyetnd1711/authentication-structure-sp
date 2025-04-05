package vn.eren.authenticationstructuresp.repository.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.eren.authenticationstructuresp.config.specification.BaseSpecification;
import vn.eren.authenticationstructuresp.entities.Roles;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RolesSpecification extends BaseSpecification<Roles> {

    public static RolesSpecification builder() {
        return new RolesSpecification();
    }

}
