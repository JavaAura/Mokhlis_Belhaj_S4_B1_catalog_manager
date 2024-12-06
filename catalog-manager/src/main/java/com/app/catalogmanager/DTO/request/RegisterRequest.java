package com.app.catalogmanager.DTO.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
    @NotBlank(message = "Login is required")
    private String login;

    @NotBlank(message = "Password is required")
    private String password;

} 