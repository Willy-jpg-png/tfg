package com.ggv.tfg.mapper;

import com.ggv.tfg.model.Order;
import com.ggv.tfg.openapi.rest.OrderCreationRest;
import com.ggv.tfg.openapi.rest.OrderRest;
import com.ggv.tfg.persistence.sqlserver.entity.OrderDao;
import com.ggv.tfg.persistence.sqlserver.entity.ProductDao;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true))
public interface OrderMapper {

    OrderDao toDao(Order order, List<ProductDao> products);

    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    @Mapping(target = "deliveryPerson", ignore = true)
    @Mapping(target = "id", ignore = true)
    Order toDomain(OrderCreationRest orderCreationRest, Long customerId, Long restaurantId);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "deliveryPersonId", source = "deliveryPerson.id")
    OrderRest toRest(Order order);



}
