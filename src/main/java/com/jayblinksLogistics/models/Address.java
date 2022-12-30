package com.jayblinksLogistics.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @NotNull(message = "Street name cannot be null")
    private String streetName;
    @NotNull(message = "City cannot be null")
    private String city;
    @NotNull(message = "Country cannot be null")
    private String Country;
    @Size(min = 1, max = 2000)
    private String no;
    @NotNull(message = "State cannot be null")
    private String state;
}
