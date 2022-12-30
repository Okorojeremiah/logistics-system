package com.jayblinksLogistics.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@Document
@NoArgsConstructor
public class Order {
    @Id
    private String orderId;
    private String senderId;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverPhoneNumber;
    private Address receiverAddress;
    private List<Item> items;
    private OrderStatus currentStatus;
    private Date timePlaced;

}
