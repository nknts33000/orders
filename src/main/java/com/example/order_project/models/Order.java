package com.example.order_project.models;

import com.example.order_project.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(
    name = "orders",
        indexes = {
            @Index(name = "idx_orders_user_created", columnList = "user_id, created_at"),
            @Index(name = "idx_orders_user_status_created", columnList = "user_id, status, created_at")
        }
)
public class Order {
    public Order(
        String description,
        User user
    ){
        this.description = description;
        this.user = user;
    }

    @PrePersist
    void prePersist() {
        createdAt = Instant.now();
        status = OrderStatus.CREATED; //runs right before Hibernate executes INSERT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
