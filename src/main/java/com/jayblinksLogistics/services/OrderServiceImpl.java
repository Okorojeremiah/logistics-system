package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.AddOrderRequest;
import com.jayblinksLogistics.dto.AddOrderResponse;
import com.jayblinksLogistics.exception.OrderNotFoundException;
import com.jayblinksLogistics.exception.UserNotFoundException;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.repository.OrderRepository;
import com.jayblinksLogistics.repository.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final SenderRepository senderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, SenderRepository senderRepository) {
        this.senderRepository = senderRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(AddOrderRequest addOrderRequest) {
        List<Item> itemList = addOrderRequest.getItems();

        Order order = new Order();
        order.setItems(itemList);
        order.setSenderId(addOrderRequest.getSenderId());
        order.setReceiverFirstName(addOrderRequest.getReceiverFirstName());
        order.setReceiverLastName(addOrderRequest.getReceiverLastName());
        order.setReceiverPhoneNumber(addOrderRequest.getReceiverPhoneNumber());
        order.setReceiverAddress(addOrderRequest.getReceiverAddress());
        order.setCurrentStatus(OrderStatus.PROCESSING);
        order.setTimePlaced(new Date());

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findOrderByOrderId(id).orElseThrow(()-> new OrderNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrdersBySenderId(String senderId) {
        Sender sender = senderRepository.findByUserId(senderId).orElseThrow(()-> new UserNotFoundException("Wrong id"));
        return orderRepository.findAllById(sender.getUserId());
    }

}
