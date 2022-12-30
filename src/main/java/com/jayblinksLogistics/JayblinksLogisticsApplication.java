package com.jayblinksLogistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@ComponentScan(basePackages = {"com.jayblinksLogistics.repository"})
@ComponentScan(basePackages = {"com.jayblinksLogistics.services"})
@ComponentScan(basePackages = {"com.jayblinksLogistics.controller"})
@ComponentScan(basePackages = {"com.jayblinksLogistics.models"})
@ComponentScan(basePackages = {"com.jayblinksLogistics.dto"})
public class JayblinksLogisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JayblinksLogisticsApplication.class, args);
    }

}
