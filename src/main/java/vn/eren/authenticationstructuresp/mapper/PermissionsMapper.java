package vn.eren.authenticationstructuresp.mapper;

import org.mapstruct.Mapper;
import vn.eren.authenticationstructuresp.config.mapper.DefaultConfigMapper;
import vn.eren.authenticationstructuresp.config.mapper.EntityMapper;
import vn.eren.authenticationstructuresp.dto.request.PermissionRequest;
import vn.eren.authenticationstructuresp.dto.response.PermissionResponse;
import vn.eren.authenticationstructuresp.entities.Permissions;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface PermissionsMapper extends EntityMapper<PermissionRequest, PermissionResponse, Permissions> {
}
