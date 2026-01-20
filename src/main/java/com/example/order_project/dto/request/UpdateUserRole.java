package com.example.order_project.dto.request;

import com.example.order_project.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRole {
    public Long userId;
    public UserRole userRole;
}
