package com.example.order_project.service.orders;

import com.example.order_project.dto.request.OrderRequest;
import com.example.order_project.dto.response.OrderResponse;
import com.example.order_project.enums.OrderStatus;
import com.example.order_project.models.User;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface OrderService<T>{
    OrderResponse create(OrderRequest orderRequestDTO, User user);
    T getOrder(Long orderId);
    OrderResponse updateOrder(OrderRequest orderRequestDTO);
    OrderResponse updateOrderStatus(OrderStatus orderStatus, long orderId);
    void deleteOrder(long orderId);
    Page<OrderResponse> getAllUserOrders(Long userId, int page, int size);
    Page<OrderResponse> getUserOrdersByStatus(Long userId, OrderStatus status, int page, int size);
}
