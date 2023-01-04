package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;

public interface UserServices {
    UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest);
    LoginResponse login(LoginRequest loginRequest);
    UpdateUserResponse update(UpdateUserRequest updateUserRequest);

}
