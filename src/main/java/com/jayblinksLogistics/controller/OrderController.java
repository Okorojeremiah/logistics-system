package com.jayblinksLogistics.controller;

import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
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
    @DeleteMapping("/deleteOrder/{orderId}")
    public  void deleteOrder(@RequestBody @PathVariable String orderId){
        orderService.deleteOrder(orderId);
    }
    @DeleteMapping("/deleteOrders")
    public  void deleteOrders(){
        orderService.deleteAllOrders();
    }



}

