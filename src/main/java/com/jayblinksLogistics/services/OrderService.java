package com.jayblinksLogistics.services;


import com.jayblinksLogistics.dto.AddOrderRequest;
import com.jayblinksLogistics.dto.AddOrderResponse;
import com.jayblinksLogistics.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order addOrder(AddOrderRequest addOrderRequest);
    Order getOrderById(String id);
    List<Order> getAllOrders();

    List<Order> getAllOrdersBySenderId(String senderId);


}
