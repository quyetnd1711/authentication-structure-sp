package vn.eren.authenticationstructuresp.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.eren.authenticationstructuresp.config.audit.AbstractAuditingEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "sp")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Roles extends AbstractAuditingEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name", length = 50, nullable = false)
    String name;

    @Column(name = "description", length = 200)
    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permissions> permissions = new HashSet<>();
}
