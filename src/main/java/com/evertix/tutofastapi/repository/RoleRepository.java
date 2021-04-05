package com.evertix.tutofastapi.repository;


import com.evertix.tutofastapi.model.Role;
import com.evertix.tutofastapi.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
