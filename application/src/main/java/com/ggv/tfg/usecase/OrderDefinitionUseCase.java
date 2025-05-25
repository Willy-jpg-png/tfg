package com.ggv.tfg.usecase;

import com.ggv.tfg.exception.OrderException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.model.Order;
import com.ggv.tfg.port.in.OrderDefinitionManagementPort;
import com.ggv.tfg.port.out.OrderDefinitionPersistencePort;
import com.ggv.tfg.stereotype.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class OrderDefinitionUseCase implements OrderDefinitionManagementPort {

    private OrderDefinitionPersistencePort orderDefinitionPersistencePort;

    @Override
    public Order createOrder(final Order order) throws ProductException, OrderException {

        return orderDefinitionPersistencePort.createOrder(order);
    }

    @Override
    public void deleteOrder(final Long orderId, final Long customerId, final Long restaurantId) throws OrderException {

        orderDefinitionPersistencePort.deleteOrder(orderId, customerId, restaurantId);
    }
}
