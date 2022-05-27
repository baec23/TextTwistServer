package com.baec.texttwist.authentication.repository;

import com.baec.texttwist.authentication.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long>
{
    AppUser findByUsername(String username);
}
