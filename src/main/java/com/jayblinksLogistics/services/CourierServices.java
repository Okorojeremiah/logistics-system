package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.response.CourierStatusResponse;
import com.jayblinksLogistics.dto.response.DeliveryResponse;
import com.jayblinksLogistics.models.*;

import java.util.List;

public interface CourierServices {
    DeliveryResponse confirmDelivery(DeliveryRequest deliveryRequest);
    DeliveryResponse acceptSenderOrder(String senderId);
    Courier findCourier(String id);
    void deleteCourier(String id);
    void save(Courier courier);
    CourierStatusResponse checkCourierStatus(String courierId);

}
