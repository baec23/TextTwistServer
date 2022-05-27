package com.baec.texttwist.authentication.service;

import com.baec.texttwist.authentication.model.Role;
import com.baec.texttwist.authentication.model.AppUser;

import java.util.List;

public interface UserService
{
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
