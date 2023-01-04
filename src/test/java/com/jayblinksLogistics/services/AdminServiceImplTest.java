package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.request.AddOrderRequest;
import com.jayblinksLogistics.dto.request.LoginRequest;
import com.jayblinksLogistics.dto.request.UpdateUserRequest;
import com.jayblinksLogistics.dto.request.UserRegistrationRequest;
import com.jayblinksLogistics.dto.response.LoginResponse;
import com.jayblinksLogistics.dto.response.UpdateUserResponse;
import com.jayblinksLogistics.dto.response.UserRegistrationResponse;
import com.jayblinksLogistics.models.*;
import com.jayblinksLogistics.repository.AdminRepository;
import com.jayblinksLogistics.repository.CourierRepository;
import com.jayblinksLogistics.repository.OrderRepository;
import com.jayblinksLogistics.repository.SenderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {
    private UserRegistrationRequest courierRegistrationRequest;
    private UserRegistrationRequest senderRegistrationRequest;
    private UserRegistrationRequest adminRegistrationRequest;

    private LoginRequest loginRequest;
    private Address address;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Qualifier("courierServiceImpl")
    @Autowired
    private UserServices courierService;

    @Qualifier("senderServiceImpl")
    @Autowired
    private UserServices senderServices;
    @Qualifier("adminServiceImpl")
    @Autowired
    private UserServices adminServices;

    @Autowired
    private AdminServices moreAdminServices;

    @Autowired
    private SenderServices moreSenderServices;

    @BeforeEach
    void setUp() {
        courierRegistrationRequest = new UserRegistrationRequest();
        address = new Address();
        address.setStreetName("Ajanoku");
        address.setCity("Ibadan");
        address.setNo("45");
        address.setState("Oyo");
        address.setCountry("Nigeria");

        courierRegistrationRequest.setFirstName("Jerry");
        courierRegistrationRequest.setLastName("Okoro");
        courierRegistrationRequest.setEmail("okorojeremiah90@gmail.com");
        courierRegistrationRequest.setAddress(address);
        courierRegistrationRequest.setPhoneNumber("08101558611");
        courierRegistrationRequest.setPassword("Slimjay91");

        senderRegistrationRequest = new UserRegistrationRequest();
        address = new Address();
        address.setStreetName("Ajanoku");
        address.setCity("Ibadan");
        address.setNo("45");
        address.setState("Oyo");
        address.setCountry("Nigeria");

        senderRegistrationRequest.setFirstName("Ade");
        senderRegistrationRequest.setLastName("Aderoju");
        senderRegistrationRequest.setEmail("adeAderoju@gmail.com");
        senderRegistrationRequest.setAddress(address);
        senderRegistrationRequest.setPhoneNumber("08101558613");
        senderRegistrationRequest.setPassword("Adeluv91");

        adminRegistrationRequest = new UserRegistrationRequest();
        address = new Address();
        address.setStreetName("Ajanoku");
        address.setCity("Ibadan");
        address.setNo("45");
        address.setState("Oyo");
        address.setCountry("Nigeria");

        adminRegistrationRequest.setFirstName("Badejo");
        adminRegistrationRequest.setLastName("Lukemon");
        adminRegistrationRequest.setEmail("bad404@gmail.com");
        adminRegistrationRequest.setAddress(address);
        adminRegistrationRequest.setPhoneNumber("08101558616");
        adminRegistrationRequest.setPassword("badbaddest91");

        loginRequest = new LoginRequest();
        loginRequest.setEmail(adminRegistrationRequest.getEmail());
        loginRequest.setPassword(adminRegistrationRequest.getPassword());
    }

    @AfterEach
    void tearDown() {
        senderRepository.deleteAll();
        courierRepository.deleteAll();
        adminRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void testThatAdminCanRegister() {
        UserRegistrationResponse response = adminServices.register(adminRegistrationRequest);
        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(response.getStatusCode(), 201);
    }

    @Test
    void testThatAdminCanLogin() {
        UserRegistrationResponse registrationResponse = adminServices.register(adminRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = adminServices.login(loginRequest);
        System.out.println(loginResponse);
        assertNotNull(loginResponse);
    }

    @Test
    void testThatAdminDetailsCanBeUpdated() {
        UserRegistrationResponse registrationResponse = adminServices.register(adminRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = adminServices.login(loginRequest);
        assertNotNull(loginResponse);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(registrationResponse.getUserId());
        updateUserRequest.setFirstName("Martha");
        updateUserRequest.setLastName("Danladi");
        updateUserRequest.setEmail("marthadanladi654@gmail.com");
        updateUserRequest.setAddress(address);
        updateUserRequest.setPhoneNumber("07041941942");
        updateUserRequest.setPassword("Maqueen25");

        UpdateUserResponse updateResponse = adminServices.update(updateUserRequest);
        assertNotNull(updateResponse);
        assertEquals(updateResponse.getStatusCode(), 201);
    }

    @Test
    void testThatAdminCanFindSenderInTheDataBase() {
        UserRegistrationResponse senderResponse = senderServices.register(senderRegistrationRequest);
        Sender sender = moreAdminServices.findSender(senderResponse.getUserId());
        assertEquals(senderResponse.getUserId(), sender.getUserId());
    }

    @Test
    void testThatAdminCanFindCourierInTheDataBase() {
        UserRegistrationResponse courierResponse = courierService.register(courierRegistrationRequest);
        Courier courier = moreAdminServices.findCourier(courierResponse.getUserId());
        assertEquals(courierResponse.getUserId(), courier.getUserId());
    }

    @Test
    void testThatAdminCanDeleteSenderFromDataBase() {
        UserRegistrationResponse senderResponse = senderServices.register(senderRegistrationRequest);
        Sender sender = moreAdminServices.findSender(senderResponse.getUserId());
        moreAdminServices.deleteSender(sender.getUserId());
        Optional<Sender> foundSender = senderRepository.findByUserId(sender.getUserId());
        assertEquals(Optional.empty(), foundSender);
    }

    @Test
    void testThatAdminCanDeleteCourierFromDataBase() {
        UserRegistrationResponse courierResponse = courierService.register(senderRegistrationRequest);
        Courier courier = moreAdminServices.findCourier(courierResponse.getUserId());
        moreAdminServices.deleteCourier(courier.getUserId());
        Optional<Sender> foundCourier = senderRepository.findByUserId(courier.getUserId());
        assertEquals(Optional.empty(), foundCourier);
    }

    @Test
    void testThatAdminCanViewAllOrdersInTheDataBase() {
        List<Order> orders = moreAdminServices.viewAllOrders();
        assertNotNull(orders);
    }

    @Test
    void testThatAdminCanFindOrdersByCurrentStatus() {
        UserRegistrationResponse senderResponse = senderServices.register(senderRegistrationRequest);

        List<Item> items = new ArrayList<>();
        Item item = new Item("Eggs", Category.FRAGILE);
        Item addItem = new Item("Bed", Category.NON_FRAGILE);
        items.add(item);
        items.add(addItem);

        List<Item> secondItems = new ArrayList<>();
        Item moreItem = new Item("Eggs", Category.FRAGILE);
        Item yetMoreItem = new Item("Bed", Category.NON_FRAGILE);
        secondItems.add(moreItem);
        secondItems.add(yetMoreItem);


        AddOrderRequest addOrderRequest = new AddOrderRequest();
        addOrderRequest.setSenderId(senderResponse.getUserId());
        addOrderRequest.setItems(items);
        addOrderRequest.setReceiverFirstName("Disu");
        addOrderRequest.setReceiverLastName("Alamin");
        addOrderRequest.setReceiverPhoneNumber("08101558612");
        addOrderRequest.setReceiverAddress(address);


        AddOrderRequest secondAddOrderRequest = new AddOrderRequest();
        secondAddOrderRequest.setSenderId(senderResponse.getUserId());
        secondAddOrderRequest.setItems(secondItems);
        secondAddOrderRequest.setReceiverFirstName("Disu");
        secondAddOrderRequest.setReceiverLastName("Alamin");
        secondAddOrderRequest.setReceiverPhoneNumber("08101558612");
        secondAddOrderRequest.setReceiverAddress(address);


        moreSenderServices.placeOrder(addOrderRequest);
        moreSenderServices.placeOrder(secondAddOrderRequest);

        List<Order> orders = moreAdminServices.findOrdersByCurrentStatus(OrderStatus.PROCESSING);
        assertNotNull(orders);
    }
}