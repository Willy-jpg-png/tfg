package com.ggv.tfg.port.out;

import com.ggv.tfg.exception.OrderException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Order;
import com.ggv.tfg.model.enums.OrderStatusEnum;
import org.springframework.data.domain.Page;

public interface OrderDefinitionPersistencePort {

    Order createOrder(Order order) throws ProductException, OrderException;

    void deleteOrder(Long orderId, Long customerId, Long restaurantId) throws OrderException;

    Page<Order> retrieveCustomerOrders(Long customerId, Integer pageNumber, Integer pageSize) throws OrderException;

    Page<Order> retrieveRestaurantOrders(Long restaurantId, Integer pageNumber, Integer pageSize) throws OrderException;

    Page<Order> retrieveDeliveryPersonOrders(Long deliveryPersonId, Integer pageNumber, Integer pageSize) throws OrderException;

    Page<Order> retrieveUnassignedOrders(Integer pageNumber, Integer pageSize) throws OrderException;

    void assignOrder(Order order, DeliveryPerson deliveryPersonId) throws OrderException;

    void updateOrderStatus(Order order, OrderStatusEnum orderStatus) throws OrderException;

}
