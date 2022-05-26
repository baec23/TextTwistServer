package com.baec.texttwist.repository;

import com.baec.texttwist.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(String name);
}
