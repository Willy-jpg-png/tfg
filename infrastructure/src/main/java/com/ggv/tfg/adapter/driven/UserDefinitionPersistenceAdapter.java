package com.ggv.tfg.adapter.driven;

import com.ggv.tfg.exception.ExceptionMessage;
import com.ggv.tfg.exception.SignInException;
import com.ggv.tfg.exception.SignUpException;
import com.ggv.tfg.exception.UserException;
import com.ggv.tfg.mapper.UserMapper;
import com.ggv.tfg.model.Customer;
import com.ggv.tfg.model.DeliveryPerson;
import com.ggv.tfg.model.Restaurant;
import com.ggv.tfg.model.User;
import com.ggv.tfg.model.enums.RoleEnum;
import com.ggv.tfg.persistence.sqlserver.entity.CustomerDao;
import com.ggv.tfg.persistence.sqlserver.entity.DeliveryPersonDao;
import com.ggv.tfg.persistence.sqlserver.entity.RestaurantDao;
import com.ggv.tfg.persistence.sqlserver.repository.CustomerRepository;
import com.ggv.tfg.persistence.sqlserver.repository.DeliveryPersonRepository;
import com.ggv.tfg.persistence.sqlserver.repository.RestaurantRepository;
import com.ggv.tfg.persistence.sqlserver.repository.UserRepository;
import com.ggv.tfg.port.out.UserDefinitionPersistencePort;
import com.ggv.tfg.stereotype.Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Adapter
@Slf4j
@Transactional
public class UserDefinitionPersistenceAdapter implements UserDefinitionPersistencePort {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public User signIn(final User user) throws SignInException {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())
                .map(userMapper::toDomain)
                .orElseThrow(() -> new SignInException(ExceptionMessage.INVALID_CREDENTIALS_EXCEPTION.getMessage()));
    }


    @Override
    public void signUpCustomer(final Customer customer) throws SignUpException {
        if (userRepository.existsByUsername(customer.getUsername()))
            throw new SignUpException(ExceptionMessage.USER_EXISTS_EXCEPTION.getMessage());

        customer.setRole(RoleEnum.CUSTOMER);
        customer.setId(null);

        final CustomerDao customerDao = userMapper.toDao(customer);
        try {
            customerRepository.save(customerDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new SignUpException(ExceptionMessage.SIGN_UP_EXCEPTION.getMessage());
        }
    }

    @Override
    public void signUpRestaurant(final Restaurant restaurant) throws SignUpException {
        if (userRepository.existsByUsername(restaurant.getUsername()))
            throw new SignUpException(ExceptionMessage.USER_EXISTS_EXCEPTION.getMessage());

        restaurant.setRole(RoleEnum.RESTAURANT);
        restaurant.setId(null);

        final RestaurantDao restaurantDao = userMapper.toDao(restaurant);
        try {
            restaurantRepository.save(restaurantDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new SignUpException(ExceptionMessage.SIGN_UP_EXCEPTION.getMessage());
        }
    }

    @Override
    public void signUpDeliveryPerson(final DeliveryPerson deliveryPerson) throws SignUpException {
        if (userRepository.existsByUsername(deliveryPerson.getUsername()))
            throw new SignUpException(ExceptionMessage.USER_EXISTS_EXCEPTION.getMessage());

        deliveryPerson.setRole(RoleEnum.DELIVERY_PERSON);
        deliveryPerson.setId(null);

        final DeliveryPersonDao deliveryPersonDao = userMapper.toDao(deliveryPerson);
        try {
            deliveryPersonRepository.save(deliveryPersonDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new SignUpException(ExceptionMessage.SIGN_UP_EXCEPTION.getMessage());
        }
    }

    @Override
    public void updateCustomer(final Customer customer, final Long userId) throws UserException {

        if (!userRepository.existsByUsernameAndPasswordAndId(customer.getUsername(), customer.getPassword(), userId)) {
            throw new UserException(ExceptionMessage.INVALID_CREDENTIALS_EXCEPTION.getMessage());
        }

        final CustomerDao existingDao = customerRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionMessage.UPDATE_USER_EXCEPTION.getMessage()));

        if (customer.getName() != null)
            existingDao.setName(customer.getName());

        if (customer.getEmail() != null)
            existingDao.setEmail(customer.getEmail());

        if (customer.getStreet() != null)
            existingDao.setStreet(customer.getStreet());

        if (customer.getNumber() != null)
            existingDao.setNumber(customer.getNumber());

        if (customer.getFloor() != null)
            existingDao.setFloor(customer.getFloor());

        if (customer.getLatitude() != null)
            existingDao.setLatitude(customer.getLatitude());

        if (customer.getLongitude() != null)
            existingDao.setLongitude(customer.getLongitude());

        try {
            customerRepository.save(existingDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UserException(ExceptionMessage.UPDATE_USER_EXCEPTION.getMessage());
        }
    }

    @Override
    public void updateRestaurant(final Restaurant restaurant, final Long userId) throws UserException {

        if (!userRepository.existsByUsernameAndPasswordAndId(restaurant.getUsername(), restaurant.getPassword(), userId)) {
            throw new UserException(ExceptionMessage.INVALID_CREDENTIALS_EXCEPTION.getMessage());
        }

        final RestaurantDao existingDao = restaurantRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionMessage.UPDATE_USER_EXCEPTION.getMessage()));

        if (restaurant.getDescription() != null)
            existingDao.setDescription(restaurant.getDescription());
        if (restaurant.getPhone() != null)
            existingDao.setPhone(restaurant.getPhone());
        if (restaurant.getWebsite() != null)
            existingDao.setWebsite(restaurant.getWebsite());
        if (restaurant.getName() != null)
            existingDao.setName(restaurant.getName());
        if (restaurant.getEmail() != null)
            existingDao.setEmail(restaurant.getEmail());

        if (restaurant.getStreet() != null)
            existingDao.setStreet(restaurant.getStreet());
        if (restaurant.getNumber() != null)
            existingDao.setNumber(restaurant.getNumber());
        if (restaurant.getFloor() != null)
            existingDao.setFloor(restaurant.getFloor());
        if (restaurant.getLatitude() != null)
            existingDao.setLatitude(restaurant.getLatitude());
        if (restaurant.getLongitude() != null)
            existingDao.setLongitude(restaurant.getLongitude());

        try {
            restaurantRepository.save(existingDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UserException(ExceptionMessage.UPDATE_USER_EXCEPTION.getMessage());
        }
    }

    @Override
    public void updateDeliveryPerson(final DeliveryPerson deliveryPerson, final Long userId) throws UserException {

        if (!userRepository.existsByUsernameAndPasswordAndId(deliveryPerson.getUsername(), deliveryPerson.getPassword(), userId)) {
            throw new UserException(ExceptionMessage.INVALID_CREDENTIALS_EXCEPTION.getMessage());
        }

        final DeliveryPersonDao existingDao = deliveryPersonRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionMessage.UPDATE_USER_EXCEPTION.getMessage()));
        if(deliveryPerson.getPhoto()!=null)
            existingDao.setPhoto(deliveryPerson.getPhoto());
        if(deliveryPerson.getName()!=null)
            existingDao.setName(deliveryPerson.getName());
        if(deliveryPerson.getEmail()!=null)
            existingDao.setEmail(deliveryPerson.getEmail());

        try {
            deliveryPersonRepository.save(existingDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UserException(ExceptionMessage.UPDATE_USER_EXCEPTION.getMessage());
        }
    }

    @Override
    public Page<Restaurant> retrieveRestaurants(final Integer pageNumber, final Integer pageSize) {
        final var pageRequest = PageRequest.of(pageNumber, pageSize);
        final var restaurantPage = restaurantRepository.findAll(pageRequest);
        return restaurantPage.map(userMapper::toDomain);
    }

}
