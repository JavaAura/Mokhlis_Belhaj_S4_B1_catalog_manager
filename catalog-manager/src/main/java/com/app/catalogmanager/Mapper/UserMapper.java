package com.app.catalogmanager.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.catalogmanager.DTO.request.UserRequest;
import com.app.catalogmanager.DTO.response.UserResponse;
import com.app.catalogmanager.Entity.Role;
import com.app.catalogmanager.Entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "rolesName", expression = "java(getRoleNames(user))")
    UserResponse toResponse(User user);

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequest userRequest);

    default List<String> getRoleNames(User user) {
        if (user.getRoles() == null) {
            return new ArrayList<>();
        }
        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
