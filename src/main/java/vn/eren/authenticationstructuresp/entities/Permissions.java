package vn.eren.authenticationstructuresp.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.eren.authenticationstructuresp.config.audit.AbstractAuditingEntity;

@Entity
@Table(name = "permissions", schema = "sp")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permissions extends AbstractAuditingEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name",  length = 50, nullable = false)
    String name;

    @Column(name = "description", length = 200)
    String description;

    @Column(name = "sequence")
    Integer sequence;

}
