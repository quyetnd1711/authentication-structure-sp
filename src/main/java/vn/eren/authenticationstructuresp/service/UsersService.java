package vn.eren.authenticationstructuresp.service;

import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.SearchUsersRequest;
import vn.eren.authenticationstructuresp.dto.request.UsersRequest;
import vn.eren.authenticationstructuresp.dto.response.UsersResponse;

public interface UsersService {

    PagingResponse<UsersResponse> getListUser(SearchUsersRequest request);

    UsersResponse getOneUser(String id);

    UsersResponse createUser(UsersRequest request);

    UsersResponse updateUser(UsersRequest request);

    UsersResponse deleteUser(String id);
}
