package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.persistence.sqlserver.entity.RestaurantDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDao, Long> {

}
