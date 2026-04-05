package com.finance.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}