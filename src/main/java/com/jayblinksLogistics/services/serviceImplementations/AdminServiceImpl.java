package com.jayblinksLogistics.services.serviceImplementations;

import com.jayblinksLogistics.dto.request.CourierRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.CourierResponse;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.exception.UserLoginException;
import com.jayblinksLogistics.exception.UserRegistrationException;
import com.jayblinksLogistics.exception.UserUpdateException;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.models.enums.CourierStatus;
import com.jayblinksLogistics.models.enums.OrderStatus;
import com.jayblinksLogistics.repository.AdminRepository;
import com.jayblinksLogistics.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        response.setStatusCode(HttpStatus.CREATED);
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
        response.setStatusCode(HttpStatus.CREATED);
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
    public void deleteSenderAccount(String userId) {
        Sender sender = findSender(userId);
        senderServices.deleteSender(sender.getUserId());
    }

    @Override
    public CourierResponse assignCourier(CourierRequest courierRequest) {
        Courier courier = courierServices.findCourier(courierRequest.getCourierId());
        Sender sender = senderServices.findSender(courierRequest.getSenderId());

        sender.getOrders().stream().filter(order -> order.getCurrentStatus()
                        .equals(OrderStatus.PROCESSING))
                .forEach(order -> courier.getSenderOrders().add(order));

        courier.setStatus(CourierStatus.ASSIGNED);
        courier.setDate(LocalDate.now());
        courierServices.save(courier);

        return CourierResponse.builder()
                .message(String.format("Courier with id %s assigned", courier.getUserId()))
                .statusCode(HttpStatus.CREATED)
                .build();
    }

    @Override
    public void deleteCourierAccount(String courierId){
        Courier courier = findCourier(courierId);
        courierServices.deleteCourier(courier.getUserId());
    }

    @Override
    public List<Order> viewAllOrders() {

        return orderServices.getAllOrders().stream()
                .filter(order -> order.getCurrentStatus()
                        .equals(OrderStatus.PROCESSING))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findOrdersByCurrentStatus(OrderStatus orderStatus) {
        return orderServices.getOrdersByCurrentStatus(orderStatus);
    }


}
