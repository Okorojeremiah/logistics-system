package com.jayblinksLogistics.services;

import com.jayblinksLogistics.models.Courier;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.OrderStatus;
import com.jayblinksLogistics.models.Sender;

import java.util.List;

public interface AdminServices {
    Sender findSender(String id);
    Courier findCourier(String id);
    void deleteSender(String userId);
    void deleteCourier(String id);
    List<Order> viewAllOrders();
    List<Order> findOrdersByCurrentStatus(OrderStatus orderStatus);
}
