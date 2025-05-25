package com.ggv.tfg.port.in;

import com.ggv.tfg.exception.SignInException;
import com.ggv.tfg.exception.SignUpException;
import com.ggv.tfg.exception.UserException;
import com.ggv.tfg.model.Customer;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Restaurant;
import com.ggv.tfg.model.User;
import org.springframework.data.domain.Page;

public interface UserDefinitionManagementPort {

    void signIn(User user) throws SignInException;

    void signUpCustomer(Customer customer) throws SignUpException;

    void signUpRestaurant(Restaurant restaurant) throws SignUpException;

    void signUpDeliveryPerson(DeliveryPerson deliveryPerson) throws SignUpException;

    void updateCustomer(Customer customer, Long userId) throws UserException;

    void updateRestaurant(Restaurant restaurant, Long userId) throws UserException;

    void updateDeliveryPerson(DeliveryPerson deliveryPerson, Long userId) throws UserException;

    Page<Restaurant> retrieveRestaurants(Integer pageNumber, Integer pageSize);
}
