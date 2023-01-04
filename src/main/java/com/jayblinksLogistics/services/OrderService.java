package com.jayblinksLogistics.services;


import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.response.DeliveryResponse;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.OrderStatus;

import java.util.List;

public interface OrderService {
    Order addOrder(AddOrderRequest addOrderRequest);
    Order getOrderById(String id);
    List<Order> getAllOrders();

    List<Order> getOrdersByCurrentStatus(OrderStatus orderStatus);
    void deleteOrder(String orderId);
    List<Order> viewOrderHistory(String senderId);
    void deleteAllOrders();
    OrderStatus checkOrderStatus(String orderId);
    DeliveryResponse confirmDelivery(DeliveryRequest deliveryRequest);



}
