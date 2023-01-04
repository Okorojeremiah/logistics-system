package com.jayblinksLogistics.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document
public class Admin extends User{
}
