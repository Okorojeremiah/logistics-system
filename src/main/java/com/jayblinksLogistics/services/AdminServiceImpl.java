package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.exception.UserLoginException;
import com.jayblinksLogistics.exception.UserRegistrationException;
import com.jayblinksLogistics.exception.UserUpdateException;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements UserServices, AdminServices {
    private final AdminRepository adminRepository;
    private final CourierServices courierServices;
    private final SenderServices senderServices;
    private final OrderService orderServices;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, CourierServices courierServices, SenderServices senderServices, OrderService orderServices){
        this.orderServices = orderServices;
        this.senderServices = senderServices;
        this.courierServices = courierServices;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        Admin admin = buildAdmin(userRegistrationRequest);

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(admin.getUserId());
        response.setMessage("Registration successful");
        response.setStatusCode(201);
        return response;
    }

    private Admin buildAdmin(UserRegistrationRequest userRegistrationRequest) {
        Admin admin = new Admin();
        admin.setFirstName(userRegistrationRequest.getFirstName());
        admin.setLastName(userRegistrationRequest.getLastName());
        if (adminRepository.findByEmail(userRegistrationRequest.getEmail()).isPresent())
            throw new UserRegistrationException("User already exist");
        else
            admin.setEmail(userRegistrationRequest.getEmail());
        if (adminRepository.findByPhoneNumber(userRegistrationRequest.getPhoneNumber()).isPresent())
            throw new UserRegistrationException("User already exist");
        else
            admin.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
        Set<Address> addressSet = admin.getAddressList();
        addressSet.add(userRegistrationRequest.getAddress());
        admin.setPassword(userRegistrationRequest.getPassword());
        adminRepository.save(admin);
        return admin;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Admin foundAdmin = adminRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UserLoginException("Wrong email address"));

        LoginResponse response = new LoginResponse();
        if (foundAdmin.getPassword().equals(loginRequest.getPassword())){
            response.setMessage("Successful login");
        }else {
            response.setMessage("Email or password is incorrect");
        }
        return response;
    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest updateUserRequest) {
        Admin foundAdmin = getAdmin(updateUserRequest);

        UpdateUserResponse response = new UpdateUserResponse();

        response.setUserId(foundAdmin.getUserId());
        response.setMessage("Update successful");
        response.setStatusCode(201);
        return response;
    }

    private Admin getAdmin(UpdateUserRequest updateUserRequest) {
        Admin foundAdmin = adminRepository.findByUserId(updateUserRequest.getId()).orElseThrow(()->new UserUpdateException("Wrong user id"));

        foundAdmin.setFirstName(updateUserRequest.getFirstName());
        foundAdmin.setLastName(updateUserRequest.getLastName());
        foundAdmin.setPhoneNumber(updateUserRequest.getPhoneNumber());
        foundAdmin.setPassword(updateUserRequest.getPassword());
        foundAdmin.setEmail(updateUserRequest.getEmail());
        Set<Address> addressSet = foundAdmin.getAddressList();
        addressSet.add(updateUserRequest.getAddress());
        adminRepository.save(foundAdmin);
        return foundAdmin;
    }
    @Override
    public Sender findSender(String id) {
        return senderServices.findSender(id);
    }

    @Override
    public Courier findCourier(String id){
        return courierServices.findCourier(id);
    }

    @Override
    public void deleteSender(String userId) {
        Sender sender = findSender(userId);
        senderServices.deleteSender(sender.getUserId());
    }

    @Override
    public void deleteCourier(String courierId){
        Courier courier = findCourier(courierId);
        courierServices.deleteCourier(courier.getUserId());
    }

    @Override
    public List<Order> viewAllOrders() {
        return orderServices.getAllOrders();
    }

    @Override
    public List<Order> findOrdersByCurrentStatus(OrderStatus orderStatus) {
        return orderServices.getOrdersByCurrentStatus(orderStatus);
    }


}
