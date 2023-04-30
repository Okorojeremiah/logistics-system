package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.request.CourierRequest;
import com.jayblinksLogistics.dto.response.CourierResponse;
import com.jayblinksLogistics.models.Courier;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.enums.OrderStatus;
import com.jayblinksLogistics.models.Sender;

import java.util.List;

public interface AdminServices {
    Sender findSender(String id);
    Courier findCourier(String id);
    void deleteSenderAccount(String userId);
    CourierResponse assignCourier(CourierRequest courierRequest);
    void deleteCourierAccount(String id);
    List<Order> viewAllOrders();
    List<Order> findOrdersByCurrentStatus(OrderStatus orderStatus);
}
