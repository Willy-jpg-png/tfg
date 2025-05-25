package com.ggv.tfg.persistence.sqlserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Adding")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddingDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurantId", referencedColumnName = "id")
    private RestaurantDao restaurant;

    private String description;

    private Double price;

    private String image;

}
