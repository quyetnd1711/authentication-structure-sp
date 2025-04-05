package vn.eren.authenticationstructuresp.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import vn.eren.authenticationstructuresp.config.paging.FilterRequest;
import vn.eren.authenticationstructuresp.entities.Roles;
import vn.eren.authenticationstructuresp.repository.specification.RolesSpecification;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchRolesRequest extends FilterRequest<Roles> {

    String name;

    String description;

    @Override
    public Specification<Roles> specification() {
        return RolesSpecification.builder()
                .with("name", "LIKE", this.name, null, null)
                .with("description", "LIKE", this.description, null, null)
                .build();
    }
}
