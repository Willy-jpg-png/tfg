package com.ggv.tfg.usecase;

import com.ggv.tfg.exception.SignInException;
import com.ggv.tfg.exception.SignUpException;
import com.ggv.tfg.exception.UserException;
import com.ggv.tfg.model.Customer;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Restaurant;
import com.ggv.tfg.model.User;
import com.ggv.tfg.port.in.UserDefinitionManagementPort;
import com.ggv.tfg.port.out.UserDefinitionPersistencePort;
import com.ggv.tfg.stereotype.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class UserDefinitionUseCase implements UserDefinitionManagementPort {

    private UserDefinitionPersistencePort userDefinitionPersistencePort;

    @Override
    public void signIn(final User user) throws SignInException {

        userDefinitionPersistencePort.signIn(user);
    }

    @Override
    public void signUpCustomer(final Customer customer) throws SignUpException {

        userDefinitionPersistencePort.signUpCustomer(customer);
    }

    @Override
    public void signUpRestaurant(final Restaurant restaurant) throws SignUpException {

        userDefinitionPersistencePort.signUpRestaurant(restaurant);
    }

    @Override
    public void signUpDeliveryPerson(final DeliveryPerson deliveryPerson) throws SignUpException {

        userDefinitionPersistencePort.signUpDeliveryPerson(deliveryPerson);
    }

    @Override
    public void updateCustomer(final Customer customer, final Long userId) throws UserException {

        userDefinitionPersistencePort.updateCustomer(customer, userId);
    }

    @Override
    public void updateRestaurant(final Restaurant restaurant, final Long userId) throws UserException {

        userDefinitionPersistencePort.updateRestaurant(restaurant, userId);
    }

    @Override
    public void updateDeliveryPerson(final DeliveryPerson deliveryPerson, final Long userId) throws UserException {

        userDefinitionPersistencePort.updateDeliveryPerson(deliveryPerson, userId);
    }

    @Override
    public Page<Restaurant> retrieveRestaurants(final Integer pageNumber, final Integer pageSize) {

        return userDefinitionPersistencePort.retrieveRestaurants(pageNumber, pageSize);
    }

}
