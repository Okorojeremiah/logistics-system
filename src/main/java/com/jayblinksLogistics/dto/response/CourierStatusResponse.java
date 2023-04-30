package com.jayblinksLogistics.dto.response;

import com.jayblinksLogistics.models.enums.CourierStatus;
import com.jayblinksLogistics.models.Order;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourierStatusResponse {
    private List<Order> assignedOrders = new ArrayList<>();
    private CourierStatus status;
    private LocalDate date;

}
