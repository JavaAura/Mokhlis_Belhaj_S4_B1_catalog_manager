package com.app.catalogmanager.Controller;

import com.app.catalogmanager.Service.UserService;
import com.app.catalogmanager.DTO.response.UserResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/users/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/admin/users/{id}/roles")
    public UserResponse assignRoleToUser(@PathVariable Long id, @RequestBody Long roleId) {
        return userService.assignRoleToUser(id, roleId);
    }

    @DeleteMapping("/admin/users/{id}/roles")
    public UserResponse removeRoleFromUser(@PathVariable Long id, @RequestBody Long roleId) {
        return userService.removeRoleFromUser(id, roleId);
    }
}
