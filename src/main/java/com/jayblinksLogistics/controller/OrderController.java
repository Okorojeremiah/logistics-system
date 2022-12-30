package com.jayblinksLogistics.controller;

import com.jayblinksLogistics.dto.AddOrderRequest;
import com.jayblinksLogistics.dto.AddOrderResponse;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.services.OrderService;
import com.jayblinksLogistics.services.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("/addOrder")
    public  ResponseEntity<Order> addOrder(@RequestBody @Valid AddOrderRequest addOrderRequest){
        return ResponseEntity.ok(orderService.addOrder(addOrderRequest));
    }
}

