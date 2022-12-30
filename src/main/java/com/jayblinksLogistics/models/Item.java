package com.jayblinksLogistics.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @NotBlank(message = "Item name cannot be null")
    private String itemName;
    private Category category;
}
