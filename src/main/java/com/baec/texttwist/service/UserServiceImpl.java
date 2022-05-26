package com.baec.texttwist.service;

import com.baec.texttwist.model.AppUser;
import com.baec.texttwist.model.Role;
import com.baec.texttwist.repository.RoleRepository;
import com.baec.texttwist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public AppUser saveUser(AppUser user)
    {
        log.info("Saving new user to database : " + user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role)
    {
        log.info("Saving new role to database : " + role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName)
    {
        log.info("Adding new role ( " + roleName + " ) to user ( " + username + " )");
        AppUser user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username)
    {
        log.info("Retrieving user ( {} )", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers()
    {
        log.info("Retrieving all users");
        return userRepository.findAll();
    }
}
