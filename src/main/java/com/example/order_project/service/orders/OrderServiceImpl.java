package com.example.order_project.service.orders;

import com.example.order_project.dto.request.OrderRequest;
import com.example.order_project.dto.response.OrderResponse;
import com.example.order_project.enums.OrderStatus;
import com.example.order_project.exceptions.OrderException;
import com.example.order_project.mappers.OrderMapper;
import com.example.order_project.models.Order;
import com.example.order_project.models.User;
import com.example.order_project.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse create(OrderRequest orderRequestDTO, User user) {
        Order newOrder = orderRepository.save(
                new Order(
                        orderRequestDTO.getDescription(),
                        user
                )
        );

        return orderMapper.toOrderResponse(newOrder);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        if(orderId == null) throw new OrderException("Order id is needed to find an order.");
        Order order = checkIfPresentOrThrow(orderId);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequestDTO) {
        Long orderId = orderRequestDTO.getOrderId();
        if(orderId == null) throw new OrderException("The order cannot be found without an order id.");
        Order order = checkIfPresentOrThrow(orderId);
        order.setDescription(orderRequestDTO.getDescription());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(updatedOrder);
    }

    @Override
    public OrderResponse updateOrderStatus(OrderStatus orderStatus, long orderId) {
        Order order = checkIfPresentOrThrow(orderId);
        order.setStatus(orderStatus);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Transactional
    public void updateOrderStatus(long userId, long orderId, OrderStatus status) {
        int updated = orderRepository.updateStatusForUser(orderId, userId, status);
        if (updated == 0) throw new OrderException("Order not found or not owned by user");
    }

    @Override
    public void deleteOrder(long orderId) {
        Order order = checkIfPresentOrThrow(orderId);
        orderRepository.delete(order);
    }

    @Override
    public Page<OrderResponse> getAllUserOrders(Long userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findByUser_UserId(userId, pageable)
                .map(orderMapper::toOrderResponse);
    }

    @Override
    public Page<OrderResponse> getUserOrdersByStatus(Long userId, OrderStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findByUser_UserIdAndStatus(userId, status, pageable)
                .map(orderMapper::toOrderResponse);
    }

    private Order checkIfPresentOrThrow(long orderId){
        Optional<Order> orderQuery = orderRepository.findOrderByOrderId(orderId);
        if(orderQuery.isEmpty()) throw new OrderException("Order not found for id="+orderId);
        return orderQuery.get();
    }
}
