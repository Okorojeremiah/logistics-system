package com.jayblinksLogistics.services;

import com.jayblinksLogistics.JayblinksLogisticsApplication;
import com.jayblinksLogistics.dto.AddOrderRequest;
import com.jayblinksLogistics.dto.AddOrderResponse;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {
    private AddOrderRequest addOrderRequest;
    @Autowired
    private OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    public OrderServiceImplTest(OrderService orderService) {
        this.orderService = orderService;
    }

    @BeforeEach
    void setUp() {
        addOrderRequest = new AddOrderRequest();

        List<Item> items = new ArrayList<>();
        Item item = new Item("Eggs", Category.FRAGILE);
        Item addItem = new Item("Bed", Category.NON_FRAGILE);
        items.add(item);
        items.add(addItem);

        Address address = new Address();
        address.setStreetName("Ajanoku");
        address.setCity("Ibadan");
        address.setNo("45");
        address.setState("Oyo");
        address.setCountry("Nigeria");

        addOrderRequest.setItems(items);
        addOrderRequest.setReceiverFirstName("Badablu");
        addOrderRequest.setReceiverLastName("Tinubu");
        addOrderRequest.setReceiverPhoneNumber("09041941941");
        addOrderRequest.setReceiverAddress(address);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addOrderTest() {
        Order order = orderService.addOrder(addOrderRequest);
        assertNotNull(order);
        assertNotNull(order.getOrderId());
    }

    @Test
    void getOrderByIdTest() {
        Order order = orderService.addOrder(addOrderRequest);
        Order foundOrder = orderService.getOrderById(order.getOrderId());
        assertNotNull(foundOrder);
    }

    @Test
    void getAllOrders() {
        List<Order> foundOrder = orderService.getAllOrders();
        assertNotNull(foundOrder);
    }

}