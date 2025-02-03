package vn.eren.authenticationstructuresp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import vn.eren.authenticationstructuresp.config.audit.AbstractAuditingEntity;

@Entity
@Table(name = "account", schema = "public")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends AbstractAuditingEntity<String> {

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

}
