package com.ggv.tfg.adapter.driving;

import com.ggv.tfg.exception.ExceptionMessage;
import com.ggv.tfg.exception.SignInException;
import com.ggv.tfg.exception.SignUpException;
import com.ggv.tfg.exception.UserException;
import com.ggv.tfg.mapper.UserMapper;
import com.ggv.tfg.model.Customer;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Restaurant;
import com.ggv.tfg.model.User;
import com.ggv.tfg.openapi.api.UsersControllerApi;
import com.ggv.tfg.openapi.rest.*;
import com.ggv.tfg.port.out.UserDefinitionPersistencePort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class UserDefinitionController implements UsersControllerApi {

    private UserDefinitionPersistencePort userDefinitionPersistencePort;
    private UserMapper userMapper;

    @Override
    public ResponseEntity<PagedRestaurantRest> retrieveRestaurants(final Long user, final Integer pageNumber, final Integer pageSize) throws Exception {

        try {
            final var page = userDefinitionPersistencePort.retrieveRestaurants(pageNumber, pageSize);

            final PagedRestaurantRest pagedRestaurantRest = userMapper.toPagedRestaurantRest(page);

            return ResponseEntity.ok(pagedRestaurantRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UserException(ExceptionMessage.RETRIEVE_RESTAURANT_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<UserRest> signIn(final SignInRest signInRest) throws SignInException {
        final User user = userMapper.toDomain(signInRest);
        final User signedInUser = userDefinitionPersistencePort.signIn(user);
        return ResponseEntity.ok(userMapper.toRest(signedInUser));
    }

    @Override
    public ResponseEntity<Void> signUpCustomer(final SignUpCustomerRest signUpCustomerRest) throws SignUpException {

        final Customer customer = userMapper.toDomain(signUpCustomerRest);
        userDefinitionPersistencePort.signUpCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signUpDeliveryPerson(final SignUpDeliveryPersonRest signUpDeliveryPersonRest) throws SignUpException {

        final DeliveryPerson deliveryPerson = userMapper.toDomain(signUpDeliveryPersonRest);
        userDefinitionPersistencePort.signUpDeliveryPerson(deliveryPerson);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signUpRestaurant(final SignUpRestaurantRest signUpRestaurantRest) throws SignUpException {

        final Restaurant restaurant = userMapper.toDomain(signUpRestaurantRest);
        userDefinitionPersistencePort.signUpRestaurant(restaurant);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateCustomer(final Long userId, final UpdateCostumerRest updateCostumerRest) throws UserException {

        final Customer customer = userMapper.toDomain(updateCostumerRest);
        userDefinitionPersistencePort.updateCustomer(customer, userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateDeliveryPerson(final Long userId, final UpdateDeliveryPersonRest updateDeliveryPersonRest) throws UserException {

        final DeliveryPerson deliveryPerson = userMapper.toDomain(updateDeliveryPersonRest);
        userDefinitionPersistencePort.updateDeliveryPerson(deliveryPerson, userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateRestaurant(final Long userId, final UpdateRestaurantRest updateRestaurantRest) throws UserException {

        final Restaurant restaurant = userMapper.toDomain(updateRestaurantRest);
        userDefinitionPersistencePort.updateRestaurant(restaurant, userId);
        return ResponseEntity.ok().build();
    }


}
