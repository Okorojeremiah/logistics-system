package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.response.DeliveryResponse;
import com.jayblinksLogistics.models.Courier;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.OrderStatus;

import java.util.List;

public interface CourierServices {
    DeliveryResponse confirmDelivery(DeliveryRequest deliveryRequest);
    Courier findCourier(String id);
    OrderStatus checkDeliveryStatus(String orderId);
    void deleteCourier(String id);

    List<Order> findOrdersByCurrentStatus(OrderStatus orderStatus);


}
