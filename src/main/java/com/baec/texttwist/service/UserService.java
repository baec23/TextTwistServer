package com.baec.texttwist.service;

import com.baec.texttwist.model.AppUser;
import com.baec.texttwist.model.Role;

import java.util.List;

public interface UserService
{
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
