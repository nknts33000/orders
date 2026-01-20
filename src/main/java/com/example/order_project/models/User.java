package com.example.order_project.models;

import com.example.order_project.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    public User(
            String username,
            String password,
            UserRole role
    ){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "user") // many products, fetching requires time, so it is set to LAZY
    private Set<Order> orders = new HashSet<>();
}

