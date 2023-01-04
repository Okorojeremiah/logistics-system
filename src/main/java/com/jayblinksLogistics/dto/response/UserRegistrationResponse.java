package com.jayblinksLogistics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRegistrationResponse {
    private int statusCode;
    private String message;
    private String userId;
}
