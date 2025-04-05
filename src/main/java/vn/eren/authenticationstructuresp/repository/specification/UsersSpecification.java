package vn.eren.authenticationstructuresp.repository.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.eren.authenticationstructuresp.config.specification.BaseSpecification;
import vn.eren.authenticationstructuresp.entities.Users;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersSpecification extends BaseSpecification<Users> {

    public static UsersSpecification builder() {
        return new UsersSpecification();
    }

}
