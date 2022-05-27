package com.baec.texttwist.authentication.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baec.texttwist.authentication.model.Role;
import com.baec.texttwist.authentication.service.UserService;
import com.baec.texttwist.authentication.util.TokenUtil;
import com.baec.texttwist.authentication.model.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers()
    {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> saveUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String displayName)
    {
        AppUser newUser = new AppUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setName(displayName);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/register").toUriString());
        AppUser returnUser = userService.saveUser(newUser);
        userService.addRoleToUser(username, "ROLE_USER");
        return ResponseEntity.created(uri).body(returnUser);
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role)
    {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form)
    {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            try
            {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedToken = TokenUtil.decodeJWT(refreshToken);
                String username = decodedToken.getSubject();
                AppUser user = userService.getUser(username);
                String accessToken = TokenUtil.generateAccessToken(request.getRequestURL().toString(), user);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch(Exception e)
            {
                log.error("Error logging in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("errorMessage", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else
        {
            throw new RuntimeException("Refresh token error");
        }
    }
}

@Data
class RoleToUserForm
{
    private String username;
    private String roleName;
}
