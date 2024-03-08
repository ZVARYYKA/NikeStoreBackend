package dev.zvaryyka.nikestorebackend.repositories;

import dev.zvaryyka.nikestorebackend.models.ERole;
import dev.zvaryyka.nikestorebackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
