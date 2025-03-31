package vn.eren.authenticationstructuresp.config.mapper;


import org.mapstruct.*;

import java.util.List;

public interface EntityMapper<REQ, RES, EN> {
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    EN toEntity(REQ req);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    RES toResponse(EN entity);

    List<RES> toResponseList(List<EN> entities);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    List<EN> toEntityList(List<REQ> reqs);


    @Named(value = "update")
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget EN entity, REQ req);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateRequest(@MappingTarget REQ req, EN entity);
}
