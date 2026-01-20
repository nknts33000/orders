package com.example.order_project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    public Long orderId;
    @NotBlank
    public String description;
}
