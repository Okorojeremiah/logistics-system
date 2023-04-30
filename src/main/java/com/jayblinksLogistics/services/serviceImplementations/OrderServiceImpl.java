package com.jayblinksLogistics.services.serviceImplementations;

import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.dto.request.DeliveryRequest;
import com.jayblinksLogistics.dto.response.DeliveryResponse;
import com.jayblinksLogistics.exception.OrderNotFoundException;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.models.enums.OrderStatus;
import com.jayblinksLogistics.repository.OrderRepository;
import com.jayblinksLogistics.services.OrderService;
import com.jayblinksLogistics.services.SenderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final SenderServices senderServices;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, SenderServices senderServices) {
        this.senderServices = senderServices;
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
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByCurrentStatus(OrderStatus orderStatus) {
        return orderRepository.findOrdersByCurrentStatus(orderStatus);
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order not found"));
        order.setCurrentStatus(OrderStatus.CANCELLED);
        orderRepository.delete(order);
    }

    @Override
    public List<Order> viewOrderHistory(String senderId) {
        Sender sender = senderServices.findSender(senderId);
        return orderRepository.findOrderBySenderId(sender.getUserId());
    }
    public void deleteAllOrders(){
        orderRepository.deleteAll();
    }

    @Override
    public OrderStatus checkOrderStatus(String orderId) {
        Order order = getOrderById(orderId);
        return order.getCurrentStatus();
    }

    @Override
    public DeliveryResponse confirmDelivery(DeliveryRequest deliveryRequest) {
        Order order = getOrderById(deliveryRequest.getOrderId());
        order.setCurrentStatus(deliveryRequest.getDeliveryStatus());
        orderRepository.save(order);

        DeliveryResponse deliveryResponse = new DeliveryResponse();
        deliveryResponse.setOrderStatus(order.getCurrentStatus());
        return deliveryResponse;
    }

}
