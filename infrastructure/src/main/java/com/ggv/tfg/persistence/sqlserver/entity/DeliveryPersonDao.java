package com.ggv.tfg.persistence.sqlserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "DeliveryPerson")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonDao extends UserDao {

    private String photo;

}
