package vn.eren.authenticationstructuresp.config.audit;


import org.springframework.data.domain.AuditorAware;
import vn.eren.authenticationstructuresp.common.util.SecurityUtil;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(SecurityUtil.getCurrentAccountId());
    }
}
