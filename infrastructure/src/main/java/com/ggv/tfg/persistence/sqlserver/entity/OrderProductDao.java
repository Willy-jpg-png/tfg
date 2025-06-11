package com.ggv.tfg.persistence.sqlserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OrderProducts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderDao order;


    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductDao product;

    private int quantity;

    @ManyToMany
    @JoinTable(
            name = "OrderProductAddings",
            joinColumns = @JoinColumn(name = "orderProductId"),
            inverseJoinColumns = @JoinColumn(name = "addingId")
    )
    private List<AddingDao> addings = new ArrayList<>();
}
