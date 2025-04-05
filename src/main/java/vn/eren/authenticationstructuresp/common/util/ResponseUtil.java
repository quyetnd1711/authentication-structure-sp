package vn.eren.authenticationstructuresp.common.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import vn.eren.authenticationstructuresp.config.audit.AbstractAuditingEntity;
import vn.eren.authenticationstructuresp.dto.response.BaseResponse;
import vn.eren.authenticationstructuresp.repository.UsersRepository;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResponseUtil {

    UsersRepository usersRepository;

    public <E, R extends BaseResponse> R convertToResponse(E entity, Function<E, R> mapper, String dateFormat) {
        R response = mapper.apply(entity);
        if (response != null && entity instanceof AbstractAuditingEntity<?> entityAudit) {
            if (entityAudit.getCreateDate() != null) {
                response.setCreateDate(DateTimeUtil.convertInstantToString(entityAudit.getCreateDate(), dateFormat));
            }
            if (entityAudit.getWriteDate() != null) {
                response.setWriteDate(DateTimeUtil.convertInstantToString(entityAudit.getWriteDate(), dateFormat));
            }
            if (entityAudit.getCreateUid() != null) {
                response.setCreateBy(usersRepository.findUsernameById(entityAudit.getCreateUid()));
            }
            if (entityAudit.getWriteUid() != null) {
                response.setWriteBy(usersRepository.findUsernameById(entityAudit.getWriteUid()));
            }
        }
        return response;
    }
}
