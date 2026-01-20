package com.example.order_project.service.user;

import com.example.order_project.enums.UserRole;
import com.example.order_project.exceptions.UserException;
import com.example.order_project.models.User;
import com.example.order_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username){
        Optional<User> userQuery = userRepository.findByUsername(username);
        if(userQuery.isEmpty()) throw new UserException("User could not be found by username "+username);
        return userQuery.get();
    }

    public void updateUserRole(UserRole userRole, Long userId){
        if(userRole == null || userId == null){
            throw new UserException("Please complete both user Id and user role.");
        }
        User user = userRepository.findById(userId).orElseThrow();
        user.setRole(userRole);
        userRepository.save(user);
    }
}
