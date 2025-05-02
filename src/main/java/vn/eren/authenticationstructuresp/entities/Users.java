package vn.eren.authenticationstructuresp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import vn.eren.authenticationstructuresp.config.audit.AbstractAuditingEntity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "sp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users extends AbstractAuditingEntity<String> {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, unique = true, length = 36)
    String id;

    @Column(name = "username", nullable = false, length = 50)
    String username;

    @Column(name = "password", nullable = false, length = 50)
    String password;

    @Column(name = "phone_number", length = 50)
    String phoneNumber;

    @Column(name = "email", length = 150)
    String email;

    @Column(name = "name", length = 120)
    String name;

    @Column(name = "is_otp", length = 1)
    String isOtp;

    @Column(name = "is_admin")
    Boolean isAdmin;

    @Column(name = "is_locked", length = 1)
    String isLocked;

    @Column(name = "time_locked_end")
    Instant timeLockedEnd;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            schema = "sp",
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();
}
