package com.baec.texttwist.authentication.repository;

import com.baec.texttwist.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(String name);
}
