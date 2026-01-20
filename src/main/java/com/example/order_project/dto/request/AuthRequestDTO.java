package com.example.order_project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
}
