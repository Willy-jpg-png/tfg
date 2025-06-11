package com.ggv.tfg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {

    private String street;
    private String number;
    private String floor;
    private Double latitude;
    private Double longitude;

}
