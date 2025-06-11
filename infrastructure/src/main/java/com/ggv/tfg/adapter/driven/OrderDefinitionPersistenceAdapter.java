package com.ggv.tfg.adapter.driven;

import com.ggv.tfg.exception.ExceptionMessage;
import com.ggv.tfg.exception.OrderException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.mapper.OrderMapper;
import com.ggv.tfg.mapper.ProductMapper;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Order;
import com.ggv.tfg.model.OrderProduct;
import com.ggv.tfg.model.enums.OrderStatusEnum;
import com.ggv.tfg.persistence.sqlserver.entity.AddingDao;
import com.ggv.tfg.persistence.sqlserver.entity.OrderDao;
import com.ggv.tfg.persistence.sqlserver.entity.OrderProductDao;
import com.ggv.tfg.persistence.sqlserver.entity.ProductDao;
import com.ggv.tfg.persistence.sqlserver.repository.AddingRepository;
import com.ggv.tfg.persistence.sqlserver.repository.OrderProductRepository;
import com.ggv.tfg.persistence.sqlserver.repository.OrdersRepository;
import com.ggv.tfg.persistence.sqlserver.repository.ProductRepository;
import com.ggv.tfg.port.out.OrderDefinitionPersistencePort;
import com.ggv.tfg.stereotype.Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Adapter
@Slf4j
@Transactional
public class OrderDefinitionPersistenceAdapter implements OrderDefinitionPersistencePort {

    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final AddingRepository addingRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    public Order createOrder(final Order order) throws ProductException, OrderException {
        double totalPrice = 0.0;
        final List<OrderProductDao> orderProductDaos = new ArrayList<>();

        for (final OrderProduct op : order.getOrderProducts()) {
            final ProductDao productDao = productMapper.toDao(op.getProduct());

            final List<AddingDao> addingDaos = op.getAddings().stream()
                    .map(a -> {
                                try {
                                    return addingRepository.findById(a.getId())
                                            .orElseThrow(() -> new ProductException(ExceptionMessage.ADDING_NOT_FOUND_EXCEPTION.getMessage()));
                                } catch (final ProductException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    ).collect(Collectors.toList());

            final OrderProductDao opDao = new OrderProductDao();
            opDao.setProduct(productDao);
            opDao.setQuantity(op.getQuantity());
            opDao.setAddings(addingDaos);

            totalPrice += productDao.getPrice() * op.getQuantity();
            for (final AddingDao a : addingDaos) {
                totalPrice += a.getPrice() * op.getQuantity();
            }

            orderProductDaos.add(opDao);
        }

        order.setTotalPrice(totalPrice);
        final OrderDao orderDao = orderMapper.toDao(order, orderProductDaos);

        for (final OrderProductDao opDao : orderProductDaos) {
            opDao.setOrder(orderDao);
        }

        try {
            ordersRepository.save(orderDao);
            return order;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.CREATE_ORDER_EXCEPTION.getMessage());
        }
    }



    @Override
    public void assignOrder(final Order order, final DeliveryPerson deliveryPerson) throws OrderException {
        order.setDeliveryPerson(deliveryPerson);

        try {
            final List<OrderProductDao> orderProductDaos = new ArrayList<>();
            for (final OrderProduct op : order.getOrderProducts()) {
                final Long productId = op.getId();
                final OrderProductDao opDao = orderProductRepository.findById(productId).orElseThrow(
                        () -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage())
                );
                orderProductDaos.add(opDao);
            }

            final OrderDao orderDao = orderMapper.toDao(order, orderProductDaos);
            ordersRepository.save(orderDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage());
        }
    }


    @Override
    public void updateOrderStatus(final Order order, final OrderStatusEnum orderStatus) throws OrderException {
        order.setOrderStatus(orderStatus);

        try {
            final List<OrderProductDao> orderProductDaos = new ArrayList<>();
            for (final OrderProduct op : order.getOrderProducts()) {
                final Long productId = op.getId();
                final OrderProductDao opDao = orderProductRepository.findById(productId).orElseThrow(
                        () -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage())
                );
                orderProductDaos.add(opDao);
            }

            final OrderDao orderDao = orderMapper.toDao(order, orderProductDaos);
            ordersRepository.save(orderDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage());
        }
    }


    @Override
    public void deleteOrder(final Long orderId, final Long customerId, final Long restaurantId) throws OrderException {

        if (!ordersRepository.existsByIdAndCustomerIdAndRestaurantId(orderId, customerId, restaurantId))
            throw new OrderException(ExceptionMessage.ORDER_NOT_FOUND_EXCEPTION.getMessage());

        try {
            ordersRepository.deleteById(orderId);
        } catch (final Exception e) {
            throw new OrderException(ExceptionMessage.ORDER_DELETE_EXCEPTION.getMessage());
        }
    }

    @Override
    public Page<Order> retrieveCustomerOrders(final Long customerId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        final var pageRequest = PageRequest.of(pageNumber, pageSize);
        final var orderPage = ordersRepository.findAllByCustomerId(customerId, pageRequest);
        return orderPage.map(orderMapper::toDomain);
    }

    @Override
    public Page<Order> retrieveRestaurantOrders(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        final var pageRequest = PageRequest.of(pageNumber, pageSize);
        final var orderPage = ordersRepository.findAllByRestaurantId(restaurantId, pageRequest);
        return orderPage.map(orderMapper::toDomain);
    }

    @Override
    public Page<Order> retrieveUnassignedOrders(final Integer pageNumber, final Integer pageSize) throws OrderException {

        final var pageRequest = PageRequest.of(pageNumber, pageSize);
        final var orderPage = ordersRepository.findAllByDeliveryPersonIdIsNull(pageRequest);
        return orderPage.map(orderMapper::toDomain);
    }

    @Override
    public Page<Order> retrieveDeliveryPersonOrders(final Long deliveryPersonId, final Integer pageNumber, final Integer pageSize) throws OrderException {

        final var pageRequest = PageRequest.of(pageNumber, pageSize);
        final var orderPage = ordersRepository.findAllByDeliveryPersonId(deliveryPersonId, pageRequest);
        return orderPage.map(orderMapper::toDomain);
    }
}
