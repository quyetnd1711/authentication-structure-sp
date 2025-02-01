package vn.eren.authenticationstructuresp.config.audit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createDate", "createUid", "writeDate", "writeId"}, allowGetters = true)
public abstract class AbstractAuditingEntity<Long> {

    @CreatedDate
    @Column(name = "create_date")
    Instant createDate;

    @CreatedBy
    @Column(name = "create_uid")
    Long createUid;

    @LastModifiedDate
    @Column(name = "write_date")
    Instant writeDate;

    @LastModifiedBy
    @Column(name = "write_uid")
    Long writeUid;
}
