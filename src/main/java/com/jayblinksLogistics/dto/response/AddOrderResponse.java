package com.jayblinksLogistics.dto.response;

import com.jayblinksLogistics.models.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddOrderResponse {
    private String message;
    private int statusCode;
    private String orderId;
    private String senderId;
    private OrderStatus orderStatus;
}
