package com.example.order_project.mappers;

import com.example.order_project.dto.response.OrderResponse;
import com.example.order_project.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // remove if you don't use Spring
public interface OrderMapper {

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    OrderResponse toOrderResponse(Order order);
}
