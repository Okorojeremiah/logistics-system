package com.jayblinksLogistics.controller;

import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.DeliveryResponse;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.OrderStatus;
import com.jayblinksLogistics.services.CourierServices;
import com.jayblinksLogistics.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UpdateUserResponse> update(@Valid @RequestBody UpdateUserRequest updateUserRequest){
        UpdateUserResponse response = userServices.update(updateUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/checkOrderStatus")
    public ResponseEntity<OrderStatus> checkOrderStatus(@RequestBody String orderId){
        OrderStatus status = courierServices.checkDeliveryStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @GetMapping("/confirmOrderStatus")
    public ResponseEntity<DeliveryResponse> confirmOrderStatus(@RequestBody @Valid DeliveryRequest deliveryRequest){
        DeliveryResponse response = courierServices.confirmDelivery(deliveryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/fetchOrdersByCurrentStatus/{orderStatus}")
    public ResponseEntity<List<Order>> orders(@RequestBody @PathVariable OrderStatus orderStatus){
        List<Order> orders = courierServices.findOrdersByCurrentStatus(orderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
