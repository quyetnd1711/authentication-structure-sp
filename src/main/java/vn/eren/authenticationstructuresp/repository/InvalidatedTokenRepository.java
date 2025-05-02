package vn.eren.authenticationstructuresp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.eren.authenticationstructuresp.entities.InvalidatedToken;

import java.util.List;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM sp.invalidated_token WHERE expiry_time < NOW()")
    List<InvalidatedToken> findTokenExpired();
}
