package com.jayblinksLogistics.controller;

import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.*;
import com.jayblinksLogistics.services.CourierServices;
import com.jayblinksLogistics.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courier")
public class CourierController {
    private final UserServices userServices;
    private final CourierServices courierServices;

    @Autowired
    public CourierController(@Qualifier("courierServiceImpl") UserServices userServices, CourierServices courierServices){
        this.userServices = userServices;
        this.courierServices = courierServices;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest){
        UserRegistrationResponse response = userServices.register(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response = userServices.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateUserResponse> updateAccount(@Valid @RequestBody UpdateUserRequest updateUserRequest){
        UpdateUserResponse response = userServices.update(updateUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/confirmOrderStatus")
    public ResponseEntity<DeliveryResponse> confirmOrderStatus(@RequestBody @Valid DeliveryRequest deliveryRequest){
        DeliveryResponse response = courierServices.confirmDelivery(deliveryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/checkMyStatus/{courierId}")
    public ResponseEntity<CourierStatusResponse> checkCourierStatus(@PathVariable String courierId){
        CourierStatusResponse response = courierServices.checkCourierStatus(courierId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/fetchOrdersByCurrentStatus/{senderId}")
    public ResponseEntity<DeliveryResponse> acceptOrder(@PathVariable String senderId){
        DeliveryResponse response = courierServices.acceptSenderOrder(senderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
