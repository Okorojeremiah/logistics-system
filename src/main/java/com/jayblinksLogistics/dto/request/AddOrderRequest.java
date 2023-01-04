package com.jayblinksLogistics.dto.request;

import com.jayblinksLogistics.models.Address;
import com.jayblinksLogistics.models.Category;
import com.jayblinksLogistics.models.Item;
import com.jayblinksLogistics.models.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderRequest {
    private String senderId;
    @NotNull(message = "Item name cannot be blank")
    private List<Item> items;
    @NotBlank(message = "Receiver first name cannot be null")
    private String receiverFirstName;
    @NotBlank(message = "Receiver last name cannot be null")
    private String receiverLastName;
    @Pattern(regexp = "^\\d{11}$", message = "Invalid phone number")
    private String receiverPhoneNumber;
    private Address receiverAddress;


}
