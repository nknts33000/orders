package com.example.order_project.controllers;

import com.example.order_project.dto.request.OrderRequest;
import com.example.order_project.dto.request.UpdateOrderStatus;
import com.example.order_project.dto.response.OrderResponse;
import com.example.order_project.enums.OrderStatus;
import com.example.order_project.models.User;
import com.example.order_project.security.jwt.JwtUtil;
import com.example.order_project.service.orders.OrderServiceImpl;
import com.example.order_project.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final JwtUtil jwtUtil;
    private final OrderServiceImpl orderServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public OrderController(
        JwtUtil jwtUtil,
        OrderServiceImpl orderServiceImpl,
        UserServiceImpl userServiceImpl
    ){
        this.jwtUtil = jwtUtil;
        this.orderServiceImpl = orderServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderRequest orderRequestDTO
    ){
        String username = jwtUtil.extractUsername(trimToken(token));
        User user = userServiceImpl.getUserByUsername(username);
        OrderResponse newOrder = orderServiceImpl.create(orderRequestDTO, user);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<OrderResponse> getOrderByOrderId(@PathVariable long orderId){
        OrderResponse order = orderServiceImpl.getOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<OrderResponse>> getAllUserOrders(
        @RequestHeader("Authorization") String token,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        String username = jwtUtil.extractUsername(trimToken(token));
        User user = userServiceImpl.getUserByUsername(username);
        Page<OrderResponse> orders = orderServiceImpl.getAllUserOrders(user.getUserId(), page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/get-by-status")
    public ResponseEntity<Page<OrderResponse>> getMyOrdersByStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String username = jwtUtil.extractUsername(trimToken(token));
        User user = userServiceImpl.getUserByUsername(username);
        Page<OrderResponse> orders =
                orderServiceImpl.getUserOrdersByStatus(
                        user.getUserId(),
                        status,
                        page,
                        size
                );

        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequestDTO){
        OrderResponse updatedOrder = orderServiceImpl.updateOrder(orderRequestDTO);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable long orderId){
        OrderResponse cancelledOrder = orderServiceImpl.updateOrderStatus(OrderStatus.CANCELLED, orderId);
        return new ResponseEntity<>(cancelledOrder, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@RequestBody UpdateOrderStatus updateOrderStatus){
        OrderResponse updatedOrder =
                orderServiceImpl.updateOrderStatus(updateOrderStatus.getStatus(), updateOrderStatus.getOrderId());
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long orderId){
        orderServiceImpl.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String trimToken(String token){
        return token.substring(7); //Removes "Bearer " from the beginning of the token string
    }
}
