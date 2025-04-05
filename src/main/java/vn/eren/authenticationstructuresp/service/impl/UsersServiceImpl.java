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
import vn.eren.authenticationstructuresp.dto.request.SearchUsersRequest;
import vn.eren.authenticationstructuresp.dto.request.UsersRequest;
import vn.eren.authenticationstructuresp.dto.response.UsersResponse;
import vn.eren.authenticationstructuresp.entities.Users;
import vn.eren.authenticationstructuresp.mapper.UsersMapper;
import vn.eren.authenticationstructuresp.repository.UsersRepository;
import vn.eren.authenticationstructuresp.service.UsersService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersServiceImpl implements UsersService {

    UsersRepository usersRepository;

    UsersMapper usersMapper;

    ResponseUtil responseUtil;

    @Override
    public PagingResponse<UsersResponse> getListUser(SearchUsersRequest request) {
        Page<UsersResponse> pageUsers = usersRepository.findAll(request.specification(), request.getPaging().pageable())
                .map(this::convertToUsersResponse);
        PagingRequest paging = request.getPaging();
        return new PagingResponse<UsersResponse>()
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
    public UsersResponse getOneUser(String id) {
        Optional<Users> user = usersRepository.findOneUserById(id);
        if (user.isPresent()) {
            return usersMapper.toResponse(user.get());
        } else {
            throw new AppException(ErrorCode.NOT_EXIST_USER);
        }
    }

    @Override
    public UsersResponse createUser(UsersRequest request) {
        Optional<Users> existsUsers = usersRepository.findByUsername(request.getUsername());
        if (existsUsers.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Users newUser = usersMapper.toEntity(request);
        return usersMapper.toResponse(usersRepository.save(newUser));
    }

    @Override
    public UsersResponse updateUser(UsersRequest request) {
        Users users = usersRepository.findById(request.getId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        usersMapper.update(users, request);
        return usersMapper.toResponse(usersRepository.save(users));

    }

    @Override
    public UsersResponse deleteUser(String id) {
        Users users = usersRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        usersRepository.deleteById(id);
        return usersMapper.toResponse(users);
    }

    private UsersResponse convertToUsersResponse(Users user) {
        return responseUtil.convertToResponse(user, usersMapper::toResponse, DateTimeConstant.DD_MM_YYYY_HH_mm_ss);
    }
}
