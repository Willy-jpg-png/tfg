package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.persistence.sqlserver.entity.OrderProductDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductDao, Long> {
}
