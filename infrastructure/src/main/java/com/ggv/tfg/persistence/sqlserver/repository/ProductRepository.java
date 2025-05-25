package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.persistence.sqlserver.entity.ProductDao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDao, Long> {

    boolean existsByIdAndRestaurantId(Long id, Long restaurantId);

    Page<ProductDao> findAllByRestaurantId(Long restaurantId, org.springframework.data.domain.Pageable pageable);

}
