package com.ggv.tfg.persistence.sqlserver.repository;

import com.ggv.tfg.persistence.sqlserver.entity.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {

    boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsernameAndPasswordAndId(String username, String password, Long id);

    boolean existsByUsername(String username);

    Optional<UserDao> findByUsernameAndPassword(String username, String password);

}
