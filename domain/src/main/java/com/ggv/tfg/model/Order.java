package com.ggv.tfg.model;

import com.ggv.tfg.model.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private Restaurant restaurant;
    private Customer customer;
    private DeliveryPerson deliveryPerson;
    private List<OrderProduct> orderProducts;
    private Double totalPrice;
    private OrderStatusEnum orderStatus = OrderStatusEnum.ON_PREPARATION;
}
