package com.app.catalogmanager.Service;

import java.util.List;


import com.app.catalogmanager.DTO.response.UserResponse;


public interface UserService {

    public List<UserResponse> getAllUsers() ;
    public UserResponse getUserById(Long id) ;
    public UserResponse assignRoleToUser(Long userId, Long roleId) ;
    public UserResponse removeRoleFromUser(Long userId, Long roleId) ;
}
