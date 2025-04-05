package vn.eren.authenticationstructuresp.mapper;

import org.mapstruct.Mapper;
import vn.eren.authenticationstructuresp.config.mapper.DefaultConfigMapper;
import vn.eren.authenticationstructuresp.config.mapper.EntityMapper;
import vn.eren.authenticationstructuresp.dto.request.UsersRequest;
import vn.eren.authenticationstructuresp.dto.response.UsersResponse;
import vn.eren.authenticationstructuresp.entities.Users;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface UsersMapper extends EntityMapper<UsersRequest, UsersResponse, Users> {
}
