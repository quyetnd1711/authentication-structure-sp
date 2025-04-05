package vn.eren.authenticationstructuresp.mapper;

import org.mapstruct.Mapper;
import vn.eren.authenticationstructuresp.config.mapper.DefaultConfigMapper;
import vn.eren.authenticationstructuresp.config.mapper.EntityMapper;
import vn.eren.authenticationstructuresp.dto.request.RolesRequest;
import vn.eren.authenticationstructuresp.dto.response.RolesResponse;
import vn.eren.authenticationstructuresp.entities.Roles;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface RolesMapper extends EntityMapper<RolesRequest, RolesResponse, Roles> {
}
