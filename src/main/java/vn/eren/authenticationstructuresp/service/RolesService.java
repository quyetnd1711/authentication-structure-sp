package vn.eren.authenticationstructuresp.service;

import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.RolesRequest;
import vn.eren.authenticationstructuresp.dto.request.SearchRolesRequest;
import vn.eren.authenticationstructuresp.dto.response.RolesResponse;

public interface RolesService {

    PagingResponse<RolesResponse> getListRole(SearchRolesRequest request);

    RolesResponse getOneRole(Long id);

    RolesResponse createRole(RolesRequest request);

    RolesResponse updateRole(RolesRequest request);

    RolesResponse deleteRole(Long id);
}
