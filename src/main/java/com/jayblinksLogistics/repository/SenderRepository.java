package com.jayblinksLogistics.repository;

import com.jayblinksLogistics.models.Sender;
import com.jayblinksLogistics.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SenderRepository extends MongoRepository<Sender, String> {
    Optional<Sender> findByEmail(String email);
    Optional<Sender> findByPhoneNumber(String phoneNumber);
    Optional<Sender> findByUserId(String id);
}