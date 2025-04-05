package vn.eren.authenticationstructuresp.service;

import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.PermissionRequest;
import vn.eren.authenticationstructuresp.dto.request.SearchPermissionsRequest;
import vn.eren.authenticationstructuresp.dto.response.PermissionResponse;

public interface PermissionsService {

    PagingResponse<PermissionResponse> getListPermission(SearchPermissionsRequest request);

    PermissionResponse getOnePermission(Long id);

    PermissionResponse createPermission(PermissionRequest request);

    PermissionResponse updatePermission(PermissionRequest request);

    PermissionResponse deletePermission(Long id);
}
