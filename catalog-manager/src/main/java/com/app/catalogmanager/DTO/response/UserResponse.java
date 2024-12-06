package com.app.catalogmanager.DTO.response;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String login;
    private List<String> rolesName;
}
