package com.jayblinksLogistics.services;

import com.jayblinksLogistics.dto.*;
import com.jayblinksLogistics.models.Address;
import com.jayblinksLogistics.repository.CourierRepository;
import com.jayblinksLogistics.repository.SenderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CourierServiceImplTest {

    private UserRegistrationRequest userRegistrationRequest;
    private LoginRequest loginRequest;
    private UpdateUserRequest updateUserRequest;
    private Address address;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private CourierServiceImpl courierService;




    @BeforeEach
    void setUp() {
        userRegistrationRequest = new UserRegistrationRequest();
        address = new Address();
        address.setStreetName("Ajanoku");
        address.setCity("Ibadan");
        address.setNo("45");
        address.setState("Oyo");
        address.setCountry("Nigeria");

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
        courierRepository.deleteAll();
    }

    @Test
    void register() {
        UserRegistrationResponse response = courierService.register(userRegistrationRequest);
        assertNotNull(response);
        assertNotNull(response.getMessage());
        assertEquals(response.getStatusCode(), 201);
    }

    @Test
    void login() {
        UserRegistrationResponse registrationResponse = courierService.register(userRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = courierService.login(loginRequest);
        System.out.println(loginResponse);
        assertNotNull(loginResponse);
    }

    @Test
    void update() {
        UserRegistrationResponse registrationResponse = courierService.register(userRegistrationRequest);
        assertNotNull(registrationResponse);

        LoginResponse loginResponse = courierService.login(loginRequest);
        assertNotNull(loginResponse);

        updateUserRequest = new UpdateUserRequest();
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
}