package com.jayblinksLogistics.repository;

import com.jayblinksLogistics.models.Admin;
import com.jayblinksLogistics.models.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByPhoneNumber(String phoneNumber);
    Optional<Admin> findByUserId(String id);
}
