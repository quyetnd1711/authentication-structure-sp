package vn.eren.authenticationstructuresp.config.audit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "createUid", "writeDate", "writeId"}, allowGetters = true)
public abstract class AbstractAuditingEntity<ID> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "create_date", nullable = false)
    Instant createDate;

    @Column(name = "create_uid", nullable = false, length = 36)
    ID createUid;

    @Column(name = "write_date", nullable = false)
    Instant writeDate;

    @Column(name = "write_uid", nullable = false, length = 36)
    ID writeUid;
}
