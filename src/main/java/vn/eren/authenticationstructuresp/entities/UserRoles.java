package vn.eren.authenticationstructuresp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles", schema = "sp")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "user_id", length = 36, nullable = false)
    String userId;

    @Column(name = "role_id", nullable = false)
    Long roleId;
}
