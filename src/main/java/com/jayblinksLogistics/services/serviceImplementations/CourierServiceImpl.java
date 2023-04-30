package com.jayblinksLogistics.services.serviceImplementations;

import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.*;
import com.jayblinksLogistics.exception.UserLoginException;
import com.jayblinksLogistics.exception.UserNotFoundException;
import com.jayblinksLogistics.exception.UserRegistrationException;
import com.jayblinksLogistics.exception.UserUpdateException;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.models.enums.CourierStatus;
import com.jayblinksLogistics.models.enums.OrderStatus;
import com.jayblinksLogistics.repository.CourierRepository;
import com.jayblinksLogistics.services.CourierServices;
import com.jayblinksLogistics.services.OrderService;
import com.jayblinksLogistics.services.SenderServices;
import com.jayblinksLogistics.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Qualifier("courierServiceImpl")
@Component
public class CourierServiceImpl implements UserServices, CourierServices {

    private final CourierRepository courierRepository;
    private final SenderServices senderServices;
    private final OrderService orderService;

    @Autowired
    public CourierServiceImpl(CourierRepository courierRepository, SenderServices senderServices, OrderService orderService){
        this.courierRepository = courierRepository;
        this.senderServices = senderServices;
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
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public CourierStatusResponse checkCourierStatus(String courierId) {
        Courier courier = findCourier(courierId);

        CourierStatusResponse response = new CourierStatusResponse();
        if (courier.getStatus() == CourierStatus.ASSIGNED) {
            courier.getSenderOrders().stream()
                    .forEach(order -> response.getAssignedOrders().add(order));
            response.setStatus(courier.getStatus());
            response.setDate(courier.getDate());
            return response;
        }

        response.setStatus(courier.getStatus());
        response.setDate(LocalDate.now());
        return response;
    }

    @Override
    public DeliveryResponse confirmDelivery(DeliveryRequest deliveryRequest) {
        return orderService.confirmDelivery(deliveryRequest);
    }

    @Override
    public DeliveryResponse acceptSenderOrder(String senderId) {
        Sender foundSender = senderServices.findSender(senderId);
        if (foundSender == null){
            throw new UserNotFoundException("Sender not found");
        }
        List<Order> orders = foundSender.getOrders();
        orders.forEach(order -> order.setCurrentStatus(OrderStatus.ENROUTE));
        orders.forEach(orderService::saveOrder);

        DeliveryResponse deliveryResponse = new DeliveryResponse();
        deliveryResponse.setMessage("Order accepted");
        deliveryResponse.setStatusCode(201);
        return deliveryResponse;
    }



}
