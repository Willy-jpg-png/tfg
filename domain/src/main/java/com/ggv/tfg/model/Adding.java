package com.ggv.tfg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adding {

    private Long id;
    private String name;
    private Restaurant restaurant;
    private String description;
    private Double price;
    private String image;

}