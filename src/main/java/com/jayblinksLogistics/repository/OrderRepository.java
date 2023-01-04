package com.jayblinksLogistics.repository;

import com.jayblinksLogistics.models.Order;
import com.jayblinksLogistics.models.OrderStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findOrderByOrderId(String id);

    List<Order> findOrderBySenderId(String senderId);

    List<Order> findOrdersByCurrentStatus(OrderStatus orderStatus);

    void deleteOrdersBySenderId(String senderId);

//    List<Order> findAll(List<Order> orders);
}
