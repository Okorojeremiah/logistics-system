package com.jayblinksLogistics.dto.response;

import com.jayblinksLogistics.models.OrderStatus;
import lombok.Data;

@Data
public class DeliveryResponse {
    private OrderStatus orderStatus;
}
