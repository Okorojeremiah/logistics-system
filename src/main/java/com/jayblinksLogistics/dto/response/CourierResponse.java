package com.jayblinksLogistics.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class CourierResponse {
    private String message;
    private HttpStatus statusCode;
}
