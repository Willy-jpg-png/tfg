package com.ggv.tfg.adapter.driving;

import com.ggv.tfg.exception.AddingException;
import com.ggv.tfg.exception.ExceptionMessage;
import com.ggv.tfg.exception.RestaurantException;
import com.ggv.tfg.exception.UserException;
import com.ggv.tfg.mapper.ProductMapper;
import com.ggv.tfg.mapper.UserMapper;
import com.ggv.tfg.model.Adding;
import com.ggv.tfg.model.Product;
import com.ggv.tfg.openapi.api.ProductsControllerApi;
import com.ggv.tfg.openapi.rest.*;
import com.ggv.tfg.persistence.sqlserver.entity.AddingDao;
import com.ggv.tfg.persistence.sqlserver.repository.AddingRepository;
import com.ggv.tfg.persistence.sqlserver.repository.RestaurantRepository;
import com.ggv.tfg.port.out.ProductDefinitionPersistencePort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductDefinitionController implements ProductsControllerApi {

    private ProductDefinitionPersistencePort productDefinitionPersistencePort;
    private ProductMapper productMapper;
    private UserMapper userMapper;
    private RestaurantRepository restaurantRepository;
    private AddingRepository addingRepository;

    @Override
    public ResponseEntity<Void> createAdding(final Long restaurantId, final AddingCreationRest addingCreationRest) throws Exception {

        final var restaurantDao = restaurantRepository.findById(restaurantId);
        if (restaurantDao.isEmpty()) {
            throw new RestaurantException(ExceptionMessage.RESTAURANT_NOT_FOUND_EXCEPTION.getMessage());
        }
        final var restaurant = userMapper.toDomain(restaurantDao.get());
        final Adding adding = productMapper.toDomain(addingCreationRest, restaurant);
        productDefinitionPersistencePort.createAdding(adding);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createProduct(final Long restaurantId, final ProductCreationRest productCreationRest) throws Exception {

        final var restaurantDao = restaurantRepository.findById(restaurantId);
        if (restaurantDao.isEmpty())
            throw new RestaurantException(ExceptionMessage.RESTAURANT_NOT_FOUND_EXCEPTION.getMessage());
        final var restaurant = userMapper.toDomain(restaurantDao.get());
        final Product product = productMapper.toDomain(productCreationRest, restaurant);
        productDefinitionPersistencePort.createProduct(product);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateProduct(final Long restaurantId, final Long productId, final ProductUpdateRest productUpdateRest) throws Exception {

        final Product product = productMapper.toDomain(productUpdateRest);
        productDefinitionPersistencePort.updateProduct(product, productId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateProductAddings(final Long restaurantId, final Long productId, final ProductUpdateRest productUpdateRest) throws Exception {

        final List<AddingDao> addingDaos = new ArrayList<>();
        for (final Integer addingId : productUpdateRest.getAddingIds()) {
            addingDaos.add(
                    addingRepository.findById(Long.valueOf(addingId))
                            .orElseThrow(() -> new AddingException(ExceptionMessage.ADDING_NOT_FOUND_EXCEPTION.getMessage()))
            );
        }

        final List<Adding> addings = addingDaos.stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());

        final Product product = productMapper.toDomain(productUpdateRest, addings);

        productDefinitionPersistencePort.updateProductAddings(product, productId, restaurantId);

        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<Void> updateAdding(final Long restaurantId, final Long addingId, final AddingUpdateRest addingUpdateRest) throws Exception {

        final Adding adding = productMapper.toDomain(addingUpdateRest);
        productDefinitionPersistencePort.updateAdding(adding, addingId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteProduct(final Long restaurantId, final Long productId) throws Exception {

        productDefinitionPersistencePort.deleteProduct(productId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PagedAddingRest> retrieveProductAddings(final Long restaurantId, final Long productId, final Integer pageNumber, final Integer pageSize) throws Exception {

        try {
            final var page = productDefinitionPersistencePort.retrieveProductAddings(restaurantId, productId, pageNumber, pageSize);

            final PagedAddingRest pagedAddingRest = productMapper.toPagedAddingRest(page);

            return ResponseEntity.ok(pagedAddingRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UserException(ExceptionMessage.RETRIEVE_ADDINGS_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<PagedAddingRest> retrieveRestaurantAddings(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws Exception {

        try {
            final var page = productDefinitionPersistencePort.retrieveRestaurantAddings(restaurantId, pageNumber, pageSize);

            final PagedAddingRest pagedAddingRest = productMapper.toPagedAddingRest(page);

            return ResponseEntity.ok(pagedAddingRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UserException(ExceptionMessage.RETRIEVE_ADDINGS_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<PagedProductRest> retrieveRestaurantProducts(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws Exception {

        try {
            final var page = productDefinitionPersistencePort.retrieveRestaurantProducts(restaurantId, pageNumber, pageSize);

            final PagedProductRest pagedRestaurantRest = productMapper.toPagedProductRest(page);

            return ResponseEntity.ok(pagedRestaurantRest);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UserException(ExceptionMessage.RETRIEVE_PRODUCTS_EXCEPTION.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> deleteAdding(final Long restaurantId, final Long addingId) throws Exception {

        productDefinitionPersistencePort.deleteAdding(addingId, restaurantId);
        return ResponseEntity.ok().build();
    }
}
