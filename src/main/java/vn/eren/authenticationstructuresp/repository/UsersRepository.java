package vn.eren.authenticationstructuresp.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.eren.authenticationstructuresp.entities.Users;

import java.util.Optional;

import static vn.eren.authenticationstructuresp.common.constant.CacheConstant.USER_BY_ID_CACHE;
import static vn.eren.authenticationstructuresp.common.constant.CacheConstant.USER_BY_USERNAME_CACHE;
import static vn.eren.authenticationstructuresp.common.constant.CacheConstant.USER_NAME_BY_ID_CACHE;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {



    @Cacheable(cacheNames = USER_BY_USERNAME_CACHE, unless = "#result == null")
    Optional<Users> findByUsername(String username);

    @Cacheable(cacheNames = USER_BY_ID_CACHE, unless = "#result == null")
    @Query(nativeQuery = true, value = "SELECT * FROM sp.users WHERE id = ?1 LIMIT 1")
    Optional<Users> findOneUserById(String id);

    @Cacheable(cacheNames = USER_NAME_BY_ID_CACHE, unless = "#result == null")
    @Query(nativeQuery = true, value = "SELECT username FROM sp.users WHERE id = ?1 LIMIT 1")
    String findUsernameById(String id);
}