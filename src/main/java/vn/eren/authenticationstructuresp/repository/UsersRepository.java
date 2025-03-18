package vn.eren.authenticationstructuresp.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.eren.authenticationstructuresp.entities.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    String USER_BY_USERNAME_CACHE = "userByUsername";

    @Cacheable(USER_BY_USERNAME_CACHE)
    Optional<Users> findByUsername(String username);
}