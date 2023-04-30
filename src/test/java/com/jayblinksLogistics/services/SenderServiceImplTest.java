package com.jayblinksLogistics.services;

import com.jayblinksLogistics.JayblinksLogisticsApplication;
import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.AddOrderResponse;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.models.Address;
import com.jayblinksLogistics.models.enums.Category;
import com.jayblinksLogistics.models.Item;
import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.repository.OrderRepository;
import com.jayblinksLogistics.repository.SenderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JayblinksLogisticsApplication.class)
class SenderServiceImplTest {
    private UserRegistrationRequest userRegistrationRequest;
    private LoginRequest loginRequest;
    private Address address;
    private List<Item> items;

    @Autowired
    private SenderRepository senderRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Qualifier("senderServiceImpl")
    @Autowired
    private UserServices userServices;

    @Autowired
    private SenderServices senderServices;



    @BeforeEach
    void setUp() {
        userRegistrationRequest = new UserRegistrationRequest();
        address = new Address();
        address.setStreetName("Ajanoku");
        address.setCity("Ibadan");
        address.setNo("45");
        address.setState("Oyo");
        address.setCountry("Nigeria");

        items = new ArrayList<>();
        Item item = new Item("Eggs", Category.FRAGILE);
        Item addItem = new Item("Bed", Category.NON_FRAGILE);
        items.add(item);
        items.add(addItem);

        userRegistrationRequest.setFirstName("Jerry");
        userRegistrationRequest.setLastName("Okoro");
        userRegistrationRequest.setEmail("okorojeremiah90@gmail.com");
        userRegistrationRequest.setAddress(address);
        userRegistrationRequest.setPhoneNumber("08101558611");
        userRegistrationRequest.setPassword("Slimjay91");

        loginRequest = new LoginRequest();
        loginRequest.setEmail(userRegistrationRequest.getEmail());
        loginRequest.setPassword(userRegistrationRequest.getPassword());
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        senderRepository.deleteAll();
    }

    @Test
    void testThatASenderCanBeRegister() {
        UserRegistrationResponse response = userServices.register(userRegistrationRequest);
        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(response.getStatusCode(), 201);
    }

    @Test
    void testThatASenderCanLogin() {
        UserRegistrationResponse registrationResponse = userServices.register(userRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = userServices.login(loginRequest);
        System.out.println(loginResponse);
        assertNotNull(loginResponse);
    }

    @Test
    void testThatASenderDetailsCanBeUpdated() {
        UserRegistrationResponse registrationResponse = userServices.register(userRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = userServices.login(loginRequest);
        assertNotNull(loginResponse);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(registrationResponse.getUserId());
        updateUserRequest.setFirstName("Martha");
        updateUserRequest.setLastName("Danladi");
        updateUserRequest.setEmail("marthadanladi654@gmail.com");
        updateUserRequest.setAddress(address);
        updateUserRequest.setPhoneNumber("07041941942");
        updateUserRequest.setPassword("Maqueen25");

        UpdateUserResponse updateResponse = userServices.update(updateUserRequest);
        assertNotNull(updateResponse);
        assertEquals(updateResponse.getStatusCode(), 201);
    }

    @Test
    void testThatASenderCanPlaceOrder(){
        UserRegistrationResponse registrationResponse = userServices.register(userRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = userServices.login(loginRequest);
        assertNotNull(loginResponse);

        AddOrderRequest addOrderRequest = new AddOrderRequest();
        addOrderRequest.setSenderId(registrationResponse.getUserId());
        addOrderRequest.setItems(items);
        addOrderRequest.setReceiverFirstName("Disu");
        addOrderRequest.setReceiverLastName("Alamin");
        addOrderRequest.setReceiverPhoneNumber("08101558612");
        addOrderRequest.setReceiverAddress(address);

        AddOrderResponse response = senderServices.placeOrder(addOrderRequest);
        assertNotNull(response);
        System.out.println(response);
    }

    @Test
    void testThatASenderCAnViewTheirOrderHistory(){
        UserRegistrationResponse registrationResponse = userServices.register(userRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = userServices.login(loginRequest);
        assertNotNull(loginResponse);

        AddOrderRequest addOrderRequest = new AddOrderRequest();
        addOrderRequest.setSenderId(registrationResponse.getUserId());
        addOrderRequest.setItems(items);
        addOrderRequest.setReceiverFirstName("Disu");
        addOrderRequest.setReceiverLastName("Alamin");
        addOrderRequest.setReceiverPhoneNumber("08101558612");
        addOrderRequest.setReceiverAddress(address);

        AddOrderResponse response = senderServices.placeOrder(addOrderRequest);
        assertNotNull(response);

        AddOrderRequest addOrderRequest2 = new AddOrderRequest();
        addOrderRequest2.setSenderId(registrationResponse.getUserId());
        addOrderRequest2.setItems(items);
        addOrderRequest2.setReceiverFirstName("Elder");
        addOrderRequest2.setReceiverLastName("Kogi");
        addOrderRequest2.setReceiverPhoneNumber("08036537905");
        addOrderRequest2.setReceiverAddress(address);

        AddOrderResponse response2 = senderServices.placeOrder(addOrderRequest2);
        assertNotNull(response2);

        List<Order> orders = senderServices.viewOrderHistory(response.getSenderId());
        assertNotNull(orders);
        System.out.println(orders);
    }
    @Test
    void testThatASenderCanCancelAnOrder() {
        UserRegistrationResponse registrationResponse = userServices.register(userRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = userServices.login(loginRequest);
        assertNotNull(loginResponse);

        AddOrderRequest addOrderRequest = new AddOrderRequest();
        addOrderRequest.setSenderId(registrationResponse.getUserId());
        addOrderRequest.setItems(items);
        addOrderRequest.setReceiverFirstName("Disu");
        addOrderRequest.setReceiverLastName("Alamin");
        addOrderRequest.setReceiverPhoneNumber("08101558612");
        addOrderRequest.setReceiverAddress(address);

        AddOrderResponse firstResponse = senderServices.placeOrder(addOrderRequest);
        assertNotNull(firstResponse);

        AddOrderRequest addOrderRequest2 = new AddOrderRequest();
        addOrderRequest2.setSenderId(registrationResponse.getUserId());
        addOrderRequest2.setItems(items);
        addOrderRequest2.setReceiverFirstName("Elder");
        addOrderRequest2.setReceiverLastName("Kogi");
        addOrderRequest2.setReceiverPhoneNumber("08036537905");
        addOrderRequest2.setReceiverAddress(address);

        AddOrderResponse secondResponse = senderServices.placeOrder(addOrderRequest2);
        assertNotNull(secondResponse);

        senderServices.cancelOrder(firstResponse.getOrderId());
        List<Order> orders = senderServices.viewOrderHistory(registrationResponse.getUserId());
        System.out.println(orders);
        Optional<Order> order = orderRepository.findOrderByOrderId(firstResponse.getOrderId());
        assertEquals(Optional.empty(), order);
    }
}