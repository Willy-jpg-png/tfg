package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.persistence.sqlserver.entity.OrderDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrderDao, Long> {

    boolean existsByIdAndCustomerIdAndRestaurantId(Long id, Long customerId, Long restaurantId);
}