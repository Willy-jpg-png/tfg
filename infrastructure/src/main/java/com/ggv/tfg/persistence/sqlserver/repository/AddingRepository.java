package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.model.Adding;
import com.ggv.tfg.persistence.sqlserver.entity.AddingDao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddingRepository extends JpaRepository<AddingDao, Long> {

    boolean existsByIdAndRestaurantId(Long id, Long restaurantId);

    Page<AddingDao> findAllByRestaurantId(Long restaurantId, org.springframework.data.domain.Pageable pageable);

}
