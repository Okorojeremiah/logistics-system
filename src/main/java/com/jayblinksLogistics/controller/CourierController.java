package com.jayblinksLogistics.controller;

import com.jayblinksLogistics.dto.*;
import com.jayblinksLogistics.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
public class CourierController {
    private UserServices userServices;

    @Autowired
    public CourierController(@Qualifier("courierServiceImpl") UserServices userServices){
        this.userServices = userServices;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> response(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest){
        UserRegistrationResponse response = userServices.register(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> response(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response = userServices.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateUserResponse> response(@Valid @RequestBody UpdateUserRequest updateUserRequest){
        UpdateUserResponse response = userServices.update(updateUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
