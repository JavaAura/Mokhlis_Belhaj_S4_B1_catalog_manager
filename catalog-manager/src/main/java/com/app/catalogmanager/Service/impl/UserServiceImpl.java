package com.app.catalogmanager.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.catalogmanager.DTO.response.UserResponse;
import com.app.catalogmanager.Entity.Role;
import com.app.catalogmanager.Entity.User;
import com.app.catalogmanager.Mapper.UserMapper;
import com.app.catalogmanager.Repository.RoleRepository;
import com.app.catalogmanager.Repository.UserRepository;
import com.app.catalogmanager.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired  
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserResponse assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId) 
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

                // cheque if role is already assigned
        if (user.getRoles().contains(role)) {
            throw new RuntimeException("Role already assigned");
        }
        user.getRoles().add(role);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().remove(role);
        return userMapper.toResponse(userRepository.save(user));
    }


}
