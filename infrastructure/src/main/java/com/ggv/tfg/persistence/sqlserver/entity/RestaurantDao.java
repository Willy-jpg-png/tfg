package com.ggv.tfg.persistence.sqlserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Restaurant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDao extends UserDao {

    private String address;
    private String description;
    private String phone;
    private String website;

}
