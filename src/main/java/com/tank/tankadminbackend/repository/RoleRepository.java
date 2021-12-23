package com.tank.tankadminbackend.repository;


import com.tank.tankadminbackend.models.ERole;
import com.tank.tankadminbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}