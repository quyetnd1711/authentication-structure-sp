package vn.eren.authenticationstructuresp.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.eren.authenticationstructuresp.common.constant.DateTimeConstant;
import vn.eren.authenticationstructuresp.common.exception.AppException;
import vn.eren.authenticationstructuresp.common.exception.ErrorCode;
import vn.eren.authenticationstructuresp.common.util.ResponseUtil;
import vn.eren.authenticationstructuresp.config.paging.PageableData;
import vn.eren.authenticationstructuresp.config.paging.PagingRequest;
import vn.eren.authenticationstructuresp.config.paging.PagingResponse;
import vn.eren.authenticationstructuresp.dto.request.RolesRequest;
import vn.eren.authenticationstructuresp.dto.request.SearchRolesRequest;
import vn.eren.authenticationstructuresp.dto.response.RolesResponse;
import vn.eren.authenticationstructuresp.entities.Permissions;
import vn.eren.authenticationstructuresp.entities.Roles;
import vn.eren.authenticationstructuresp.mapper.RolesMapper;
import vn.eren.authenticationstructuresp.repository.PermissionsRepository;
import vn.eren.authenticationstructuresp.repository.RolesRepository;
import vn.eren.authenticationstructuresp.service.RolesService;

import java.util.LinkedHashSet;
import java.util.List;

import static vn.eren.authenticationstructuresp.common.constant.CacheConstant.ROLE_BY_ID_CACHE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolesServiceImpl implements RolesService {

    RolesRepository rolesRepository;

    PermissionsRepository permissionsRepository;

    RolesMapper rolesMapper;

    ResponseUtil responseUtil;


    @Override
    public PagingResponse<RolesResponse> getListRole(SearchRolesRequest request) {
        Page<RolesResponse> pageRoles = rolesRepository.findAll(request.specification(), request.getPaging().pageable())
                .map(this::convertToRole);
        PagingRequest paging = request.getPaging();
        return new PagingResponse<RolesResponse>()
                .setContents(pageRoles.getContent())
                .setPaging(
                        new PageableData()
                                .setPageNumber(paging.getPage() - 1)
                                .setTotalPage(pageRoles.getTotalPages())
                                .setPageSize(paging.getSize())
                                .setTotalRecord(pageRoles.getTotalElements())
                );
    }

    @Override
    @Cacheable(cacheNames = ROLE_BY_ID_CACHE, unless = "#result == null")
    public RolesResponse getOneRole(Long id) {
        Roles role = rolesRepository.getOneById(id);
        if (role == null) {
            throw new AppException(ErrorCode.NOT_EXIST_PERMISSION);
        }
        return convertToRole(role);
    }

    @Override
    public RolesResponse createRole(RolesRequest request) {
        Roles existsRoles = rolesRepository.getOneByName(request.getName());
        if (existsRoles != null) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Roles newRole = rolesMapper.toEntity(request);
        if (request.getPermissionIds() != null) {
            List<Permissions> permissions = permissionsRepository.findAllById(request.getPermissionIds());
            if (permissions.isEmpty()) {
                throw new AppException(ErrorCode.NOT_EXIST_PERMISSION);
            }
            newRole.setPermissions(new LinkedHashSet<>(permissions));
        }
        Roles savedRole = rolesRepository.save(newRole);
        return convertToRole(savedRole);
    }

    @Override
    public RolesResponse updateRole(RolesRequest request) {
        Roles role = rolesRepository.getOneById(request.getId());
        if (role == null) throw new AppException(ErrorCode.NOT_EXIST_ROLE);
        Roles roleWithSameName  = rolesRepository.getOneByName(request.getName());
        if (roleWithSameName != null && !roleWithSameName.getId().equals(request.getId())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        if (request.getPermissionIds() != null) {
            List<Permissions> permissions = permissionsRepository.findAllById(request.getPermissionIds());
            if (permissions.isEmpty()) {
                throw new AppException(ErrorCode.NOT_EXIST_PERMISSION);
            }
            role.setPermissions(new LinkedHashSet<>(permissions));
        }
        rolesMapper.update(role, request);
        return convertToRole(rolesRepository.save(role));
    }

    @Override
    public RolesResponse deleteRole(Long id) {
        Roles role = rolesRepository.getOneById(id);
        if (role == null) throw new AppException(ErrorCode.NOT_EXIST_ROLE);
        if (role.getIsRoleAdmin()) throw new AppException(ErrorCode.NOT_DELETE_ROLE_ADMIN);
        rolesRepository.delete(role);
        return convertToRole(role);
    }

    private RolesResponse convertToRole(Roles roles) {
        return responseUtil.convertToResponse(roles, rolesMapper::toResponse, DateTimeConstant.DD_MM_YYYY_HH_mm_ss);
    }
}
