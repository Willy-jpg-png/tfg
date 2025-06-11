package com.ggv.tfg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    private Long id;
    private Product product;
    private int quantity;
    private List<Adding> addings;
}
