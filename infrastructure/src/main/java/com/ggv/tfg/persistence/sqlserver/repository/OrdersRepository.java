package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.persistence.sqlserver.entity.OrderDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrderDao, Long> {

    boolean existsByIdAndCustomerIdAndRestaurantId(Long id, Long customerId, Long restaurantId);

    Page<OrderDao> findAllByRestaurantId(Long restaurantId, Pageable pageable);

    Page<OrderDao> findAllByCustomerId(Long customerId, Pageable pageable);

    Page<OrderDao> findAllByDeliveryPersonIdIsNull(Pageable pageable);

    Page<OrderDao> findAllByDeliveryPersonId(Long deliveryPersonId, Pageable pageable);

    OrderDao findOrderByIdAndDeliveryPersonId(Long id, Long deliveryPersonId);

    OrderDao findOrderByIdAndDeliveryPersonIdIsNull(Long id);

}