package com.jayblinksLogistics.dto.response;

import com.jayblinksLogistics.models.enums.OrderStatus;
import lombok.Data;

@Data
public class DeliveryResponse {
    private OrderStatus orderStatus;
    private String message;
    private int statusCode;
}
