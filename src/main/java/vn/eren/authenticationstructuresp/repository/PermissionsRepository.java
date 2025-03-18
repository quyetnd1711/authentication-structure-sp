package vn.eren.authenticationstructuresp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.eren.authenticationstructuresp.entities.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}