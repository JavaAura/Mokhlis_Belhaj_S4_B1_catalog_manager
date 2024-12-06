package com.app.catalogmanager.Service.impl;

import com.app.catalogmanager.DTO.request.AuthRequest;
import com.app.catalogmanager.DTO.request.RegisterRequest;
import com.app.catalogmanager.DTO.response.AuthResponse;
import com.app.catalogmanager.Entity.Role;
import com.app.catalogmanager.Entity.User;
import com.app.catalogmanager.Exception.BadRequestException;
import com.app.catalogmanager.Repository.RoleRepository;
import com.app.catalogmanager.Repository.UserRepository;
import com.app.catalogmanager.Service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new BadRequestException("Login already exists");
        }
        
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        Optional<Role> userRole = roleRepository.findByName("USER");
        if (!userRole.isPresent()) {
            throw new BadRequestException("Default role not found");
        }
      
        user.setRoles(Arrays.asList(userRole.get()));
        userRepository.save(user);
        log.info("New user registered: {}", request.getLogin());
        
        return new AuthResponse("Registration successful");
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        log.info("User {} attempting to login", request.getLogin());
        
        Optional<User> userOptional = userRepository.findByLogin(request.getLogin());
        if (!userOptional.isPresent() || 
            !passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
            log.warn("Failed login attempt for user: {}", request.getLogin());
            throw new BadRequestException("Invalid credentials");
        }

        // Create authentication token and set it in security context
        List<SimpleGrantedAuthority> authorities = userOptional.get().getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
        
        Authentication auth = new UsernamePasswordAuthenticationToken(
            userOptional.get().getLogin(), 
            null, 
            authorities
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        log.info("User {} logged in successfully", request.getLogin());
        return new AuthResponse("Login successful");
    }

    @Override
    public AuthResponse logout() {
        SecurityContextHolder.clearContext();
        log.info("User logged out successfully");
        return new AuthResponse("Logout successful");
    }

    private boolean isValidPassword(String password) {
        return password != null && 
               password.length() >= 8 && 
               password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*[0-9].*");
    }
} 