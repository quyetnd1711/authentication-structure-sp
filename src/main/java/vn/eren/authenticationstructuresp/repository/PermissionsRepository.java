package vn.eren.authenticationstructuresp.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.eren.authenticationstructuresp.entities.Permissions;

import static vn.eren.authenticationstructuresp.common.constant.CacheConstant.PERMISSION_BY_NAME_CACHE;
import static vn.eren.authenticationstructuresp.common.constant.CacheConstant.PERMISSION_BY_ID_CACHE;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long>, JpaSpecificationExecutor<Permissions> {



    @Cacheable(cacheNames = PERMISSION_BY_ID_CACHE, unless = "#result == null")
    @Query(nativeQuery = true, value = "SELECT * FROM sp.permissions WHERE id = ?1 LIMIT 1")
    Permissions getOneById(Long id);

    @Cacheable(cacheNames = PERMISSION_BY_NAME_CACHE, unless = "#result == null")
    @Query(nativeQuery = true, value = "SELECT * FROM sp.permissions WHERE name = ?1 LIMIT 1")
    Permissions getOneByName(String name);
}