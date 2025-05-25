package com.ggv.tfg.port.out;

import com.ggv.tfg.exception.OrderException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.model.Order;

public interface OrderDefinitionPersistencePort {

    Order createOrder(Order order) throws ProductException, OrderException;

    void deleteOrder(Long orderId, Long customerId, Long restaurantId) throws OrderException;
}
