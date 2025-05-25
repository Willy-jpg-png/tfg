package com.ggv.tfg.adapter.driving;

import com.ggv.tfg.exception.ExceptionMessage;
import com.ggv.tfg.exception.OrderException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.mapper.OrderMapper;
import com.ggv.tfg.model.Order;
import com.ggv.tfg.openapi.api.OrdersControllerApi;
import com.ggv.tfg.openapi.rest.OrderCreationRest;
import com.ggv.tfg.openapi.rest.OrderRest;
import com.ggv.tfg.openapi.rest.PagedOrderRest;
import com.ggv.tfg.persistence.sqlserver.repository.CustomerRepository;
import com.ggv.tfg.persistence.sqlserver.repository.RestaurantRepository;
import com.ggv.tfg.port.out.OrderDefinitionPersistencePort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class OrderDefinitionController implements OrdersControllerApi {

    private OrderMapper orderMapper;
    private OrderDefinitionPersistencePort orderDefinitionPersistencePort;
    private CustomerRepository customerRepository;
    private RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<OrderRest> createOrder(final Long customerId, final Long restaurantId, final OrderCreationRest orderCreationRest) throws ProductException, OrderException {

        if (!customerRepository.existsById(customerId) || !restaurantRepository.existsById(restaurantId))
            throw new OrderException(ExceptionMessage.INVALID_CREDENTIALS_EXCEPTION.getMessage());
        Order order = orderMapper.toDomain(orderCreationRest, customerId, restaurantId);
        order = orderDefinitionPersistencePort.createOrder(order);
        return ResponseEntity.ok(orderMapper.toRest(order));

    }

    @Override
    public ResponseEntity<Void> deleteOrder(final Long orderId, final Long customerId, final Long restaurantId) throws OrderException {

        orderDefinitionPersistencePort.deleteOrder(orderId, customerId, restaurantId);
        return ResponseEntity.ok().build();
    }

//    @Override
//    public ResponseEntity<PagedOrderRest> retrieveDeliveryPersonOrders(final Long deliveryPersonId, final Integer pageNumber, final Integer pageSize) throws OrderException {
//
//        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//        final Page<Order> orderPage = orderDefinitionPersistencePort.retrieveDeliveryPersonOrders(deliveryPersonId, pageable);
//
//        return ResponseEntity.ok(orderMapper.toPagedRest(orderPage));
//    }
//
//    @Override
//    public ResponseEntity<PagedOrderRest> retrieveRestaurantOrders(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws OrderException {
//
//        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//        final Page<Order> orderPage = orderDefinitionPersistencePort.retrieveRestaurantOrders(restaurantId, pageable);
//
//        return ResponseEntity.ok(orderMapper.toPagedRest(orderPage));
//    }
}
