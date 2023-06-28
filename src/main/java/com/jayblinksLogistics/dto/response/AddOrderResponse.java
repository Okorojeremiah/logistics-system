package com.jayblinksLogistics.dto.response;

import com.jayblinksLogistics.models.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class AddOrderResponse {
    private String message;
    private HttpStatus statusCode;
    private String orderId;
    private String senderId;
    private OrderStatus orderStatus;
}
