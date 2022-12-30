package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.*;
import com.jayblinksLogistics.models.User;

public interface UserServices {
    UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest);
    LoginResponse login(LoginRequest loginRequest);
    UpdateUserResponse update(UpdateUserRequest updateUserRequest);
    User getUserById(String id);
    void deleteUser(String userId);
    void deleteAllUser();
}
