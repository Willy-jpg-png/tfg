package com.ggv.tfg.persistence.sqlserver.entity;

import com.ggv.tfg.model.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurantId", referencedColumnName = "id")
    private RestaurantDao restaurant;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private CustomerDao customer;

    @ManyToOne
    @JoinColumn(name = "deliveryPersonId", referencedColumnName = "id")
    private DeliveryPersonDao deliveryPerson;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductDao> orderProducts;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;
}
