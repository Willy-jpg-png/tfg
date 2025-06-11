package com.ggv.tfg.mapper;

import com.ggv.tfg.model.Customer;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Restaurant;
import com.ggv.tfg.model.User;
import com.ggv.tfg.openapi.rest.*;
import com.ggv.tfg.persistence.sqlserver.entity.CustomerDao;
import com.ggv.tfg.persistence.sqlserver.entity.DeliveryPersonDao;
import com.ggv.tfg.persistence.sqlserver.entity.RestaurantDao;
import com.ggv.tfg.persistence.sqlserver.entity.UserDao;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true))
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    Customer toDomain(SignUpCustomerRest signUpCustomerRest);

    @Mapping(target = "role", ignore = true)
    Restaurant toDomain(SignUpRestaurantRest signUpRestaurantRest);

    @Mapping(target = "role", ignore = true)
    DeliveryPerson toDomain(SignUpDeliveryPersonRest signUpDeliveryPersonRest);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toDomain(SignInRest signInRest);

    @Mapping(target = "id", ignore = true)
    UserDao toUserDao(Customer customer);

    @Mapping(target = "id", ignore = true)
    UserDao toUserDao(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    UserDao toUserDao(DeliveryPerson deliveryPerson);

    CustomerDao toDao(Customer customer);

    RestaurantDao toDao(Restaurant restaurant);

    DeliveryPersonDao toDao(DeliveryPerson deliveryPerson);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    Customer toDomain(UpdateCostumerRest updateCostumerRest);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    Restaurant toDomain(UpdateRestaurantRest updateRestaurantRes);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    DeliveryPerson toDomain(UpdateDeliveryPersonRest updateDeliveryPersonRest);

    Restaurant toDomain(RestaurantDao restaurantDao);

    UserRest toRest(User user);

    User toDomain(UserDao userDao);

    Customer toDomain(CustomerDao customerDao);

    PagedRestaurantRest toPagedRestaurantRest(Page<Restaurant> restaurantPage);

    DeliveryPerson toDomain(DeliveryPersonDao deliveryPersonDao);


}
