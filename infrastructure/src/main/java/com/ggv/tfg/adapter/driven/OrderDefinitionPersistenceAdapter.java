package com.ggv.tfg.adapter.driven;

import com.ggv.tfg.exception.ExceptionMessage;
import com.ggv.tfg.exception.OrderException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.mapper.OrderMapper;
import com.ggv.tfg.model.Order;
import com.ggv.tfg.persistence.sqlserver.entity.OrderDao;
import com.ggv.tfg.persistence.sqlserver.entity.ProductDao;
import com.ggv.tfg.persistence.sqlserver.repository.OrdersRepository;
import com.ggv.tfg.persistence.sqlserver.repository.ProductRepository;
import com.ggv.tfg.port.out.OrderDefinitionPersistencePort;
import com.ggv.tfg.stereotype.Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Adapter
@Slf4j
@Transactional
public class OrderDefinitionPersistenceAdapter implements OrderDefinitionPersistencePort {

    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order createOrder(final Order order) throws ProductException, OrderException {

        final List<ProductDao> products = new ArrayList<>();
        double price = 0.0;
        for (final Long productId : order.getProductIds()) {
            final var productDao = productRepository.findById(productId);
            if (productDao.isEmpty())
                throw new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage());
            products.add(productDao.get());
            price = price + productDao.get().getPrice();
        }
        order.setTotalPrice(price);
        final OrderDao orderDao = orderMapper.toDao(order, products);

        try {
            ordersRepository.save(orderDao);
            return order;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new OrderException(ExceptionMessage.CREATE_ORDER_EXCEPTION.getMessage());
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
}
