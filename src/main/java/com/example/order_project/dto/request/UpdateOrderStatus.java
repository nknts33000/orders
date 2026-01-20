package com.example.order_project.dto.request;

import com.example.order_project.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatus {
    @NotNull
    public Long orderId;
    @NotNull
    public OrderStatus status;
}
