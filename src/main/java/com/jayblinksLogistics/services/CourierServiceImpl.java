package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.*;
import com.jayblinksLogistics.exception.UserLoginException;
import com.jayblinksLogistics.exception.UserRegistrationException;
import com.jayblinksLogistics.exception.UserUpdateException;
import com.jayblinksLogistics.models.Address;
import com.jayblinksLogistics.models.Courier;
import com.jayblinksLogistics.models.Sender;
import com.jayblinksLogistics.models.User;
import com.jayblinksLogistics.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Component
public class CourierServiceImpl implements UserServices{

    private CourierRepository courierRepository;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository){
        this.courierRepository = courierRepository;
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        Courier courier = buildCourier(userRegistrationRequest);

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(courier.getUserId());
        response.setMessage("Registration successful");
        response.setStatusCode(201);
        return response;
    }

    private Courier buildCourier(UserRegistrationRequest userRegistrationRequest) {
        Courier courier = new Courier();
        courier.setFirstName(userRegistrationRequest.getFirstName());
        courier.setLastName(userRegistrationRequest.getLastName());
        if (courierRepository.findByEmail(userRegistrationRequest.getEmail()).isPresent())
            throw new UserRegistrationException("User already exist");
        else
            courier.setEmail(userRegistrationRequest.getEmail());
        if (courierRepository.findByPhoneNumber(userRegistrationRequest.getPhoneNumber()).isPresent())
            throw new UserRegistrationException("User already exist");
        else
            courier.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
        Set<Address> addressSet = courier.getAddressList();
        addressSet.add(userRegistrationRequest.getAddress());
        courier.setPassword(userRegistrationRequest.getPassword());
        courierRepository.save(courier);
        return courier;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Courier foundCourier = courierRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UserLoginException("Wrong email address"));

        LoginResponse response = new LoginResponse();
        if (foundCourier.getPassword().equals(loginRequest.getPassword())){
            response.setMessage("Successful login");
        }else {
            response.setMessage("Email or password is incorrect");
        }
        return response;
    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest updateUserRequest) {
        Courier foundCourier = getCourier(updateUserRequest);

        UpdateUserResponse response = new UpdateUserResponse();

        response.setUserId(foundCourier.getUserId());
        response.setMessage("Update successful");
        response.setStatusCode(201);
        return response;
    }

    private Courier getCourier(UpdateUserRequest updateUserRequest) {
        Courier foundCourier = courierRepository.findByUserId(updateUserRequest.getId()).orElseThrow(()->new UserUpdateException("Wrong user id"));

        foundCourier.setFirstName(updateUserRequest.getFirstName());
        foundCourier.setLastName(updateUserRequest.getLastName());
        foundCourier.setPhoneNumber(updateUserRequest.getPhoneNumber());
        foundCourier.setPassword(updateUserRequest.getPassword());
        foundCourier.setEmail(updateUserRequest.getEmail());
        Set<Address> addressSet = foundCourier.getAddressList();
        addressSet.add(updateUserRequest.getAddress());
        courierRepository.save(foundCourier);
        return foundCourier;
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void deleteAllUser() {

    }
}
