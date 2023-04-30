package com.jayblinksLogistics.services;


import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.dto.response.AddOrderResponse;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.enums.OrderStatus;
import com.jayblinksLogistics.models.Sender;

import java.util.List;

public interface SenderServices {
    List<Order> viewOrderHistory(String senderId);
    void cancelOrder(String orderId);
    AddOrderResponse placeOrder(AddOrderRequest addOrderRequest);

    OrderStatus checkDeliveryStatus(String orderId);
    Sender findSender(String id);

    void deleteSender(String id);
}
