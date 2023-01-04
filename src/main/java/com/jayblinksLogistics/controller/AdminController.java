package com.jayblinksLogistics.controller;

import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.models.Courier;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.OrderStatus;
import com.jayblinksLogistics.models.Sender;
import com.jayblinksLogistics.services.AdminServices;
import com.jayblinksLogistics.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserServices userServices;
    private final AdminServices adminServices;

    @Autowired
    public AdminController(@Qualifier("adminServiceImpl") UserServices userServices, AdminServices adminServices){
        this.userServices = userServices;
        this.adminServices = adminServices;
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

    @GetMapping("/fetchOrdersByCurrentStatus/{orderStatus}")
    public ResponseEntity<List<Order>> orders(@RequestBody @PathVariable OrderStatus orderStatus){
        List<Order> orders = adminServices.findOrdersByCurrentStatus(orderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<Order>> viewAllOrders(){
        return ResponseEntity.ok(adminServices.viewAllOrders());
    }

    @GetMapping("/findSender/{senderId}")
    public ResponseEntity<Sender> findSender(@RequestBody @PathVariable String senderId){
        return ResponseEntity.ok(adminServices.findSender(senderId));
    }

    @GetMapping("/findCourier/{courierId}")
    public ResponseEntity<Courier> findCourier(@RequestBody @PathVariable String courierId){
        return ResponseEntity.ok(adminServices.findCourier(courierId));
    }

    @DeleteMapping("/deleteSender/{senderId}")
    public void deleteSender(@RequestBody @PathVariable String senderId){
        adminServices.deleteSender(senderId);
    }

    @DeleteMapping("/deleteCourier/{courierId}")
    public void deleteCourier(@RequestBody @PathVariable String courierId){
        adminServices.deleteSender(courierId);
    }
}
