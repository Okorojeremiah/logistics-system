package com.jayblinksLogistics.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CourierResponse {
    private String message;
    private int statusCode;
}
