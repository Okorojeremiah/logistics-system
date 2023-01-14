package com.jayblinksLogistics.services.serviceImplementations;

import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.DeliveryResponse;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.exception.UserLoginException;
import com.jayblinksLogistics.exception.UserRegistrationException;
import com.jayblinksLogistics.exception.UserUpdateException;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.repository.CourierRepository;
import com.jayblinksLogistics.services.CourierServices;
import com.jayblinksLogistics.services.OrderService;
import com.jayblinksLogistics.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Qualifier("courierServiceImpl")
@Component
public class CourierServiceImpl implements UserServices, CourierServices {

    private final CourierRepository courierRepository;
    private final OrderService orderService;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository, OrderService orderService){
        this.courierRepository = courierRepository;
        this.orderService = orderService;
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
    public Courier findCourier(String id){
        return courierRepository.findByUserId(id).orElseThrow(()->new UserUpdateException("Wrong user id"));
    }

    @Override
    public void deleteCourier(String id){
        courierRepository.deleteById(id);
    }

    @Override
    public List<Order> findOrdersByCurrentStatus(OrderStatus orderStatus) {
        return orderService.getOrdersByCurrentStatus(orderStatus);
    }

    @Override
    public DeliveryResponse confirmDelivery(DeliveryRequest deliveryRequest) {
        return orderService.confirmDelivery(deliveryRequest);
    }

    @Override
    public OrderStatus checkDeliveryStatus(String orderId){
        return orderService.checkOrderStatus(orderId);
    }
}
