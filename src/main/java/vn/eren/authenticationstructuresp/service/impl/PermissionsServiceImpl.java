package vn.eren.authenticationstructuresp.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.eren.authenticationstructuresp.common.constant.DateTimeConstant;
import vn.eren.authenticationstructuresp.common.exception.AppException;
import vn.eren.authenticationstructuresp.common.exception.ErrorCode;
import vn.eren.authenticationstructuresp.common.util.ResponseUtil;
import vn.eren.authenticationstructuresp.config.paging.PageableData;
import vn.eren.authenticationstructuresp.config.paging.PagingRequest;
import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.PermissionRequest;
import vn.eren.authenticationstructuresp.dto.request.SearchPermissionsRequest;
import vn.eren.authenticationstructuresp.dto.response.PermissionResponse;
import vn.eren.authenticationstructuresp.entities.Permissions;
import vn.eren.authenticationstructuresp.mapper.PermissionsMapper;
import vn.eren.authenticationstructuresp.repository.PermissionsRepository;
import vn.eren.authenticationstructuresp.service.PermissionsService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionsServiceImpl implements PermissionsService{

    PermissionsRepository permissionsRepository;

    PermissionsMapper permissionsMapper;

    ResponseUtil responseUtil;

    @Override
    public PagingResponse<PermissionResponse> getListPermission(SearchPermissionsRequest request) {
        Page<PermissionResponse> pageUsers = permissionsRepository.findAll(request.specification(), request.getPaging().pageable())
                .map(this::convertToPermission);
        PagingRequest paging = request.getPaging();
        return new PagingResponse<PermissionResponse>()
                .setContents(pageUsers.getContent())
                .setPaging(
                        new PageableData()
                                .setPageNumber(paging.getPage() - 1)
                                .setTotalPage(pageUsers.getTotalPages())
                                .setPageSize(paging.getSize())
                                .setTotalRecord(pageUsers.getTotalElements())
                );
    }

    @Override
    public PermissionResponse getOnePermission(Long id) {
        Permissions permission = permissionsRepository.getOneById(id);
        if (permission == null) {
            throw new AppException(ErrorCode.NOT_EXIST_PERMISSION);
        }
        return convertToPermission(permission);
    }

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        Permissions existsPermissions = permissionsRepository.getOneByName(request.getName());
        if (existsPermissions != null) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permissions newPermission = permissionsMapper.toEntity(request);
        Permissions savedPermissions = permissionsRepository.save(newPermission);
        return convertToPermission(savedPermissions);
    }

    @Override
    public PermissionResponse updatePermission(PermissionRequest request) {
        Permissions permission = permissionsRepository.getOneById(request.getId());
        if (permission == null) {
            throw new AppException(ErrorCode.NOT_EXIST_PERMISSION);
        }
        permissionsMapper.update(permission, request);
        return convertToPermission(permissionsRepository.save(permission));
    }

    @Override
    public PermissionResponse deletePermission(Long id) {
        Permissions permission = permissionsRepository.getOneById(id);
        if (permission == null) {
            throw new AppException(ErrorCode.NOT_EXIST_PERMISSION);
        }
        permissionsRepository.delete(permission);
        return convertToPermission(permission);
    }

    private PermissionResponse convertToPermission(Permissions permissions) {
        return responseUtil.convertToResponse(permissions, permissionsMapper::toResponse, DateTimeConstant.DD_MM_YYYY_HH_mm_ss);
    }
}
