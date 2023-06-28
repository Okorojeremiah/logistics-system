package com.jayblinksLogistics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRegistrationResponse {
    private HttpStatus statusCode;
    private String message;
    private String userId;
}
