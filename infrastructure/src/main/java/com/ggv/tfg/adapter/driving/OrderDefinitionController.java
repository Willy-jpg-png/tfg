package com.ggv.tfg.adapter.driving;

import com.ggv.tfg.exception.*;
import com.ggv.tfg.mapper.OrderMapper;
import com.ggv.tfg.mapper.ProductMapper;
import com.ggv.tfg.mapper.UserMapper;
import com.ggv.tfg.model.*;
import com.ggv.tfg.model.enums.OrderStatusEnum;
import com.ggv.tfg.openapi.api.OrdersControllerApi;
import com.ggv.tfg.openapi.rest.*;
import com.ggv.tfg.persistence.sqlserver.entity.*;
import com.ggv.tfg.persistence.sqlserver.repository.*;
import com.ggv.tfg.port.out.OrderDefinitionPersistencePort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
public class OrderDefinitionController implements OrdersControllerApi {

    private final OrdersRepository ordersRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final OrderMapper orderMapper;
    private final OrderDefinitionPersistencePort orderDefinitionPersistencePort;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserMapper userMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AddingRepository addingRepository;


    @Override
    public ResponseEntity<OrderRest> createOrder(
            final Long customerId,
            final Long restaurantId,
            final OrderCreationRest orderCreationRest)
            throws ProductException, OrderException, UserException {

        if (!customerRepository.existsById(customerId) || !restaurantRepository.existsById(restaurantId)) {
            throw new OrderException(ExceptionMessage.INVALID_CREDENTIALS_EXCEPTION.getMessage());
        }

        final Customer customer = userMapper.toDomain(
                customerRepository.findById(customerId)
                        .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION.getMessage()))
        );
        final Restaurant restaurant = userMapper.toDomain(
                restaurantRepository.findById(restaurantId)
                        .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION.getMessage()))
        );

        final List<OrderProduct> orderProducts = new ArrayList<>();
        for (final var p : orderCreationRest.getProducts()) {
            final ProductDao productDao = productRepository.findById(p.getProductId())
                    .orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));
            final Product product = productMapper.toDomain(productDao);

            final List<Adding> addings = p.getAddingIds() != null ?
                    p.getAddingIds().stream().map(id -> {
                        final AddingDao addingDao;
                        try {
                            addingDao = addingRepository.findById(id)
                                    .orElseThrow(() -> new ProductException(ExceptionMessage.ADDING_NOT_FOUND_EXCEPTION.getMessage()));
                        } catch (final ProductException e) {
                            throw new RuntimeException(e);
                        }
                        return productMapper.toDomain(addingDao);
                    }).collect(Collectors.toList()) : new ArrayList<>();

            final OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setAddings(addings);
            orderProduct.setQuantity(p.getQuantity());

            orderProducts.add(orderProduct);
        }

        final Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setOrderProducts(orderProducts);

        final Order createdOrder = orderDefinitionPersistencePort.createOrder(order);
        return ResponseEntity.ok(orderMapper.toRest(createdOrder));
    }



    @Override
    public ResponseEntity<Void> deleteOrder(final Long orderId, final Long customerId, final Long restaurantId) throws OrderException {

        orderDefinitionPersistencePort.deleteOrder(orderId, customerId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PagedOrderRest> retrieveCustomerOrders(final Long customerId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        try {
            final var page = orderDefinitionPersistencePort.retrieveCustomerOrders(customerId, pageNumber, pageSize);

            final PagedOrderRest pagedOrderRest = orderMapper.toPagedOrderRest(page);

            return ResponseEntity.ok(pagedOrderRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.RETRIEVE_ORDERS_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<PagedOrderRest> retrieveDeliveryPersonOrders(final Long deliveryPersonId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        try {
            final var page = orderDefinitionPersistencePort.retrieveDeliveryPersonOrders(deliveryPersonId, pageNumber, pageSize);

            final PagedOrderRest pagedOrderRest = orderMapper.toPagedOrderRest(page);

            return ResponseEntity.ok(pagedOrderRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.RETRIEVE_ORDERS_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> assignOrder(final Long orderId, final Long deliveryPersonId) throws OrderException {

        try {
            final OrderDao orderDao = ordersRepository.findOrderByIdAndDeliveryPersonIdIsNull(orderId);
            final Order order = orderMapper.toDomain(orderDao);
            final DeliveryPersonDao deliveryPersonDao = deliveryPersonRepository.findById(deliveryPersonId).
                    orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION.getMessage()));
            final DeliveryPerson deliveryPerson = userMapper.toDomain(deliveryPersonDao);
            orderDefinitionPersistencePort.assignOrder(order, deliveryPerson);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            throw new OrderException(ExceptionMessage.ORDER_NOT_FOUND_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> updateOrderStatus(final Long orderId, final Long deliveryPersonId, final OrderStatusUpdateRequest orderStatusUpdateRequest) throws Exception {

        try {
            final OrderDao orderDao = ordersRepository.findOrderByIdAndDeliveryPersonId(orderId, deliveryPersonId);
            final Order order = orderMapper.toDomain(orderDao);
            final OrderStatusEnum orderStatus = orderMapper.toOrderStatusEnum(orderStatusUpdateRequest);
            orderDefinitionPersistencePort.updateOrderStatus(order, orderStatus);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            throw new OrderException(ExceptionMessage.ORDER_NOT_FOUND_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<PagedOrderRest> retrieveRestaurantOrders(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        try {
            final var page = orderDefinitionPersistencePort.retrieveRestaurantOrders(restaurantId, pageNumber, pageSize);

            final PagedOrderRest pagedOrderRest = orderMapper.toPagedOrderRest(page);

            return ResponseEntity.ok(pagedOrderRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.RETRIEVE_ORDERS_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<PagedOrderRest> retrieveUnassignedOrders(final Integer pageNumber, final Integer pageSize) throws Exception {

        try {
            final var page = orderDefinitionPersistencePort.retrieveUnassignedOrders(pageNumber, pageSize);

            final PagedOrderRest pagedOrderRest = orderMapper.toPagedOrderRest(page);

            return ResponseEntity.ok(pagedOrderRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.RETRIEVE_ORDERS_EXCEPTION.getMessage());
        }
    }
}
