package com.jayblinksLogistics.dto.request;

import com.jayblinksLogistics.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PaymentRequest {
    private String itemName;
    private Category category;
}
