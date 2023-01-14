package com.jayblinksLogistics.services.serviceImplementations;

import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.AddOrderResponse;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.exception.*;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.repository.SenderRepository;
import com.jayblinksLogistics.services.OrderService;
import com.jayblinksLogistics.services.SenderServices;
import com.jayblinksLogistics.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

//@Qualifier("senderServiceImpl")
@Component
public class SenderServiceImpl implements UserServices, SenderServices {

    @Autowired
    private SenderRepository senderRepository;

    private final OrderService orderService;
    @Lazy
    @Autowired
    public  SenderServiceImpl(OrderService orderService){
        this.orderService = orderService;
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        Sender sender = buildSender(userRegistrationRequest);

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(sender.getUserId());
        response.setMessage("Registration successful");
        response.setStatusCode(201);
        return response;
    }

    private Sender buildSender(UserRegistrationRequest userRegistrationRequest) {
        Sender sender = new Sender();
        sender.setFirstName(userRegistrationRequest.getFirstName());
        sender.setLastName(userRegistrationRequest.getLastName());
        if (senderRepository.findByEmail(userRegistrationRequest.getEmail()).isPresent())
            throw new UserRegistrationException("User already exist");
        else
            sender.setEmail(userRegistrationRequest.getEmail());
        if (senderRepository.findByPhoneNumber(userRegistrationRequest.getPhoneNumber()).isPresent())
            throw new UserRegistrationException("User already exist");
        else
            sender.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
        Set<Address> addressSet = sender.getAddressList();
        addressSet.add(userRegistrationRequest.getAddress());
        sender.setPassword(userRegistrationRequest.getPassword());
        senderRepository.save(sender);
        return sender;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Sender foundSender = senderRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new UserLoginException("Wrong email address"));

        LoginResponse response = new LoginResponse();
        if (foundSender.getPassword().equals(loginRequest.getPassword())){
            response.setMessage("Successful login");
        }else {
            response.setMessage("Email or password is incorrect");
        }
        return response;
    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest updateUserRequest) {
        Sender foundSender = getSender(updateUserRequest);

        UpdateUserResponse response = new UpdateUserResponse();

        response.setUserId(foundSender.getUserId());
        response.setMessage("Update successful");
        response.setStatusCode(201);
        return response;
    }

    private Sender getSender(UpdateUserRequest updateUserRequest) {
        Sender foundSender = senderRepository.findByUserId(updateUserRequest.getId()).orElseThrow(()->new UserUpdateException("Wrong user id"));

        foundSender.setFirstName(updateUserRequest.getFirstName());
        foundSender.setLastName(updateUserRequest.getLastName());
        foundSender.setPhoneNumber(updateUserRequest.getPhoneNumber());
        foundSender.setPassword(updateUserRequest.getPassword());
        foundSender.setEmail(updateUserRequest.getEmail());
        Set<Address> addressSet = foundSender.getAddressList();
        addressSet.add(updateUserRequest.getAddress());
        senderRepository.save(foundSender);
        return foundSender;
    }
    @Override
    public Sender findSender(String id){
        return senderRepository.findByUserId(id).orElseThrow(()->new UserUpdateException("Wrong user id"));
    }
    @Override
    public void deleteSender(String id){
        Sender sender = senderRepository.findByUserId(id).orElseThrow(()->new UserNotFoundException("Sender not found"));
        senderRepository.delete(sender);
    }

    public AddOrderResponse placeOrder(AddOrderRequest addOrderRequest){
        Sender sender = senderRepository.findByUserId(addOrderRequest.getSenderId()).orElseThrow(()-> new UserNotFoundException("Sender not found"));
        Order order = orderService.addOrder(addOrderRequest);

        AddOrderResponse response = new AddOrderResponse();
        response.setOrderId(order.getOrderId());
        response.setSenderId(sender.getUserId());
        response.setStatusCode(201);
        response.setOrderStatus(OrderStatus.PROCESSING);
        response.setMessage("Order successful");

        return response;
    }
    public void cancelOrder(String orderId){
        orderService.deleteOrder(orderId);
    }

    public List<Order> viewOrderHistory(String senderId){
        return orderService.viewOrderHistory(senderId);
    }
}
