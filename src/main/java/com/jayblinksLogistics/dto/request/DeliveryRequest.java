package com.jayblinksLogistics.dto.request;

import com.jayblinksLogistics.models.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    @NotBlank(message = "Id cannot be null")
    private String orderId;
    private OrderStatus deliveryStatus;
}
