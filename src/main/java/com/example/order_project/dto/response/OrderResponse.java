package com.example.order_project.dto.response;

import com.example.order_project.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    public long orderId;
    public String description;
    public OrderStatus status;
}
