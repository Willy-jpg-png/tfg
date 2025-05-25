package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.persistence.sqlserver.entity.DeliveryPersonDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPersonDao, Long> {
}
