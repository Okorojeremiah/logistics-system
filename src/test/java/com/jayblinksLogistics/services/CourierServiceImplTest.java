package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.request.*;
import com.jayblinksLogistics.dto.response.*;
import com.jayblinksLogistics.models.Address;
import com.jayblinksLogistics.models.enums.Category;
import com.jayblinksLogistics.models.Item;
import com.jayblinksLogistics.repository.CourierRepository;
import com.jayblinksLogistics.repository.SenderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CourierServiceImplTest {

    private UserRegistrationRequest courierRegistrationRequest;
    private UserRegistrationRequest senderRegistrationRequest;
    private LoginRequest courierLoginRequest;
    private LoginRequest senderLoginRequest;
    private Address address;

    @Qualifier("senderServiceImpl")
    @Autowired
    private UserServices senderServices;

    @Autowired
    private SenderServices moreSenderServices;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private SenderRepository senderRepository;

    @Qualifier("courierServiceImpl")
    @Autowired
    private UserServices courierService;

    @Autowired
    private CourierServices moreCourierService;

    private List<Item> items;




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

        courierLoginRequest = new LoginRequest();
        courierLoginRequest.setEmail(courierRegistrationRequest.getEmail());
        courierLoginRequest.setPassword(courierRegistrationRequest.getPassword());


        senderRegistrationRequest = new UserRegistrationRequest();
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

        senderRegistrationRequest.setFirstName("Ade");
        senderRegistrationRequest.setLastName("Aderoju");
        senderRegistrationRequest.setEmail("adeAderoju@gmail.com");
        senderRegistrationRequest.setAddress(address);
        senderRegistrationRequest.setPhoneNumber("08101558613");
        senderRegistrationRequest.setPassword("Adeluv91");

        senderLoginRequest = new LoginRequest();
        senderLoginRequest.setEmail(senderRegistrationRequest.getEmail());
        senderLoginRequest.setPassword(senderRegistrationRequest.getPassword());

    }

    @AfterEach
    void tearDown() {
        senderRepository.deleteAll();
        courierRepository.deleteAll();
    }

    @Test
    void testThatACourierCanBeRegister() {
        UserRegistrationResponse response = courierService.register(courierRegistrationRequest);
        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(response.getStatusCode(), 201);
    }

    @Test
    void testThatACourierCanLogin() {
        UserRegistrationResponse registrationResponse = courierService.register(courierRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = courierService.login(courierLoginRequest);
        System.out.println(loginResponse);
        assertNotNull(loginResponse);
    }

    @Test
    void testThatACourierDetailsCanBeUpdated() {
        UserRegistrationResponse registrationResponse = courierService.register(courierRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = courierService.login(courierLoginRequest);
        assertNotNull(loginResponse);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(registrationResponse.getUserId());
        updateUserRequest.setFirstName("Martha");
        updateUserRequest.setLastName("Danladi");
        updateUserRequest.setEmail("marthadanladi654@gmail.com");
        updateUserRequest.setAddress(address);
        updateUserRequest.setPhoneNumber("07041941942");
        updateUserRequest.setPassword("Maqueen25");

        UpdateUserResponse updateResponse = courierService.update(updateUserRequest);
        assertNotNull(updateResponse);
        assertEquals(updateResponse.getStatusCode(), 201);
    }

    @Test
    void testThatCourierCanCheckDeliveryStatus(){
        courierService.register(courierRegistrationRequest);
        courierService.login(courierLoginRequest);

        UserRegistrationResponse registrationResponse = senderServices.register(senderRegistrationRequest);
        senderServices.login(senderLoginRequest);

        AddOrderRequest addOrderRequest = new AddOrderRequest();
        addOrderRequest.setSenderId(registrationResponse.getUserId());
        addOrderRequest.setItems(items);
        addOrderRequest.setReceiverFirstName("Disu");
        addOrderRequest.setReceiverLastName("Alamin");
        addOrderRequest.setReceiverPhoneNumber("08101558612");
        addOrderRequest.setReceiverAddress(address);

        AddOrderResponse orderResponse = moreSenderServices.placeOrder(addOrderRequest);

//        OrderStatus orderStatus = moreCourierService.checkDeliveryStatus(orderResponse.getOrderId());
//        assertEquals(OrderStatus.PROCESSING, orderStatus);
    }

    @Test
    void testThatCourierCanConfirmDeliveryStatus(){
        courierService.register(courierRegistrationRequest);
        courierService.login(courierLoginRequest);

        UserRegistrationRequest newSenderRegistrationRequest = new UserRegistrationRequest();
        address = new Address();
        address.setStreetName("Ajanoku");
        address.setCity("Ibadan");
        address.setNo("45");
        address.setState("Oyo");
        address.setCountry("Nigeria");

        newSenderRegistrationRequest.setFirstName("felix");
        newSenderRegistrationRequest.setLastName("Bambi");
        newSenderRegistrationRequest.setEmail("felixBambi23@gmail.com");
        newSenderRegistrationRequest.setAddress(address);
        newSenderRegistrationRequest.setPhoneNumber("08101558614");
        newSenderRegistrationRequest.setPassword("Adeluv51");

        UserRegistrationResponse registrationResponse = senderServices.register(newSenderRegistrationRequest);

        AddOrderRequest addOrderRequest = new AddOrderRequest();
        addOrderRequest.setSenderId(registrationResponse.getUserId());
        addOrderRequest.setItems(items);
        addOrderRequest.setReceiverFirstName("Disu");
        addOrderRequest.setReceiverLastName("Alamin");
        addOrderRequest.setReceiverPhoneNumber("08101558612");
        addOrderRequest.setReceiverAddress(address);

        AddOrderResponse orderResponse = moreSenderServices.placeOrder(addOrderRequest);

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setOrderId(orderResponse.getOrderId());
        deliveryRequest.setDeliveryStatus(orderResponse.getOrderStatus());

        DeliveryResponse deliveryResponse = moreCourierService.confirmDelivery(deliveryRequest);
        assertNotNull(deliveryResponse);
    }

}