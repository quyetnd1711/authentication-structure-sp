package vn.eren.authenticationstructuresp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "role_permissions", schema = "sp")
public class RolePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "role_id", nullable = false)
    Long roleId;

    @Column(name = "permission_id", nullable = false)
    Long permissionId;
}
