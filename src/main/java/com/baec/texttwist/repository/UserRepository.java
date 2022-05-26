package com.baec.texttwist.repository;

import com.baec.texttwist.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long>
{
    AppUser findByUsername(String username);
}
