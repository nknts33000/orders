package com.example.order_project.controllers;

import com.example.order_project.dto.request.UpdateUserRole;
import com.example.order_project.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @PutMapping("/change-status")
    public ResponseEntity<Void> updateUserStatus(@RequestBody UpdateUserRole updateUserRole){
        System.out.println("In admin controller");
        userServiceImpl.updateUserRole(updateUserRole.getUserRole(), updateUserRole.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
