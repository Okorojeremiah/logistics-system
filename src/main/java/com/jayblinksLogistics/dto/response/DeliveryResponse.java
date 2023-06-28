package com.jayblinksLogistics.dto.response;

import com.jayblinksLogistics.models.enums.OrderStatus;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DeliveryResponse {
    private OrderStatus orderStatus;
    private String message;
    private HttpStatus statusCode;
}
