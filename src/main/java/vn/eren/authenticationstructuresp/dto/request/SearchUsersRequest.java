package vn.eren.authenticationstructuresp.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import vn.eren.authenticationstructuresp.config.paging.FilterRequest;
import vn.eren.authenticationstructuresp.entities.Users;
import vn.eren.authenticationstructuresp.repository.specification.UsersSpecification;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUsersRequest extends FilterRequest<Users> {

    String username;

    String email;

    @Override
    public Specification<Users> specification() {
        return UsersSpecification.builder()
                .with("username", "LIKE", this.username, null, null)
                .with("email", "LIKE", this.email, null, null)
                .build();
    }
}
