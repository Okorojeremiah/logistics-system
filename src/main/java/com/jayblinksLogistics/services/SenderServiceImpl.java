package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.*;
import com.jayblinksLogistics.exception.*;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.repository.OrderRepository;
import com.jayblinksLogistics.repository.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class SenderServiceImpl implements UserServices{

    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
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

    public AddOrderResponse placeOrder(AddOrderRequest addOrderRequest){
        Sender sender = senderRepository.findByUserId(addOrderRequest.getSenderId()).orElseThrow(()-> new UserNotFoundException("Sender not found"));
        Order order = orderService.addOrder(addOrderRequest);
        sender.getOrders().add(order);

        AddOrderResponse response = new AddOrderResponse();
        response.setOrderId(order.getOrderId());
        response.setSenderId(sender.getUserId());
        response.setStatusCode(201);
        response.setOrderStatus(OrderStatus.PROCESSING);
        response.setMessage("Order successful");

        return response;
    }

//    public void CancelOrder(String orderId){
//        orderRepository.deleteById(orderId);
//    }
//
//    public void cancelAllOrders(String senderId){
//        Sender sender = senderRepository.findByUserId(senderId).orElseThrow(()-> new UserNotFoundException("Wrong id"));
//        List<Order> orders = sender.getOrders();
//        orderRepository.deleteAll(orders);
//    }

    public List<Order> viewOrderHistory(String senderId){
        Sender sender = senderRepository.findByUserId(senderId).orElseThrow(()-> new UserNotFoundException("Wrong id"));
        return orderRepository.findAllById(sender.getUserId());
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
