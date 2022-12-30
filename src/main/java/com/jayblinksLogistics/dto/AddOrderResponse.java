package com.jayblinksLogistics.dto;

import com.jayblinksLogistics.models.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class AddOrderResponse {
    private String message;
    private int statusCode;
    private String orderId;
    private String senderId;
    private OrderStatus orderStatus;
}
