package com.jayblinksLogistics.dto.request;

import com.jayblinksLogistics.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String id;
    @NotNull(message = "First name cannot be null")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    private String lastName;
    @Email(regexp ="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email")
    private String email;
    @Pattern(regexp = "^\\d{11}$", message = "Invalid phone number")
    private String phoneNumber;
    @Size(min = 8, max = 15, message = "Invalid password")
    private String password;
    private Address address;
}
