package com.ggv.tfg.usecase;

import com.ggv.tfg.exception.OrderException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Order;
import com.ggv.tfg.model.enums.OrderStatusEnum;
import com.ggv.tfg.port.in.OrderDefinitionManagementPort;
import com.ggv.tfg.port.out.OrderDefinitionPersistencePort;
import com.ggv.tfg.stereotype.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

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

    @Override
    public Page<Order> retrieveCustomerOrders(final Long customerId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        return orderDefinitionPersistencePort.retrieveCustomerOrders(customerId, pageNumber, pageSize);
    }

    @Override
    public Page<Order> retrieveRestaurantOrders(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        return orderDefinitionPersistencePort.retrieveRestaurantOrders(restaurantId, pageNumber, pageSize);
    }

    @Override
    public Page<Order> retrieveDeliveryPersonOrders(final Long deliveryPersonId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        return orderDefinitionPersistencePort.retrieveDeliveryPersonOrders(deliveryPersonId, pageNumber, pageSize);
    }

    @Override
    public Page<Order> retrieveUnassignedOrders(final Integer pageNumber, final Integer pageSize) throws OrderException {

        return orderDefinitionPersistencePort.retrieveUnassignedOrders(pageNumber, pageSize);
    }

    @Override
    public void assignOrder(final Order order, final DeliveryPerson deliveryPerson) throws OrderException {

        orderDefinitionPersistencePort.assignOrder(order, deliveryPerson);
    }

    @Override
    public void updateOrderStatus(final Order order, final OrderStatusEnum orderStatus) throws OrderException {

        orderDefinitionPersistencePort.updateOrderStatus(order, orderStatus);
    }
}
