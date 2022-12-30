package com.jayblinksLogistics.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private int weight;
    private BigDecimal price;
    private String item;
    private Category category;
}
