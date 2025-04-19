package vn.eren.authenticationstructuresp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.eren.authenticationstructuresp.entities.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {

    @Query(nativeQuery = true, value = "SELECT * FROM sp.roles WHERE id = ?1 LIMIT 1")
    Roles getOneById(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM sp.roles WHERE name = ?1 LIMIT 1")
    Roles getOneByName(String name);
}