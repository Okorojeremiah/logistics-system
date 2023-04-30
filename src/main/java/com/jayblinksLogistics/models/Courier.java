package com.jayblinksLogistics.models;

import com.jayblinksLogistics.models.enums.CourierStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@Document
@NoArgsConstructor
public class Courier extends User{
    private Payment calculatePayment;
    private CourierStatus status = CourierStatus.UNASSIGNED;
    private List<Order> senderOrders = new ArrayList<>();
    private LocalDate date;
}
