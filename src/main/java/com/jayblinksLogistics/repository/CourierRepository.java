package com.jayblinksLogistics.repository;

import com.jayblinksLogistics.models.Courier;
import com.jayblinksLogistics.models.Sender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends MongoRepository<Courier, String> {
    Optional<Courier> findByEmail(String email);
    Optional<Courier> findByPhoneNumber(String phoneNumber);
    Optional<Courier> findByUserId(String id);
}
