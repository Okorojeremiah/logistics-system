package com.jayblinksLogistics.controller;


import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.AddOrderResponse;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.enums.OrderStatus;
import com.jayblinksLogistics.services.SenderServices;
import com.jayblinksLogistics.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Sender")
public class SenderController {

    private final UserServices userServices;
    private final SenderServices senderServices;

    @Autowired
    public SenderController(@Qualifier("senderServiceImpl") UserServices userServices, SenderServices senderServices){
        this.userServices = userServices;
        this.senderServices = senderServices;
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

    @GetMapping("/viewOrderHistory/{senderId}")
    public ResponseEntity<List<Order>> viewOrderHistory(@PathVariable String senderId){
        List<Order> orders = senderServices.viewOrderHistory(senderId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/checkOrderStatus/{orderId}")
    public ResponseEntity<OrderStatus> checkOrderStatus(@PathVariable String orderId){
        OrderStatus status = senderServices.checkDeliveryStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
    @DeleteMapping("/cancelOrder/{orderId}")
    public void cancelOrder(@PathVariable String orderId){
        senderServices.cancelOrder(orderId);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<AddOrderResponse> placeOrder(@RequestBody @Valid AddOrderRequest addOrderRequest){
        AddOrderResponse response = senderServices.placeOrder(addOrderRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}

