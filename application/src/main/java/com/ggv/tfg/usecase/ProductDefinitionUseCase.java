package com.ggv.tfg.usecase;

import com.ggv.tfg.exception.AddingException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.model.Adding;
import com.ggv.tfg.model.Product;
import com.ggv.tfg.port.in.ProductDefinitionManagementPort;
import com.ggv.tfg.port.out.ProductDefinitionPersistencePort;
import com.ggv.tfg.stereotype.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class ProductDefinitionUseCase implements ProductDefinitionManagementPort {

    private ProductDefinitionPersistencePort productDefinitionPersistencePort;

    @Override
    public void createProduct(final Product product) throws ProductException, AddingException {

        productDefinitionPersistencePort.createProduct(product);
    }

    @Override
    public void createAdding(final Adding adding) throws ProductException {

        productDefinitionPersistencePort.createAdding(adding);
    }

    @Override
    public void updateProduct(final Product product, final Long productId, final Long restaurantId) throws ProductException {

        productDefinitionPersistencePort.updateProduct(product, productId, restaurantId);
    }

    @Override
    public void updateProductAddings(final Product product, final Long productId, final Long restaurantId) throws ProductException, AddingException {

        productDefinitionPersistencePort.updateProductAddings(product, productId, restaurantId);
    }

    @Override
    public void updateAdding(final Adding adding, final Long addingId, final Long restaurantId) throws AddingException {

        productDefinitionPersistencePort.updateAdding(adding, addingId, restaurantId);
    }

    @Override
    public void deleteProduct(final Long productId, final Long restaurantId) throws ProductException {

        productDefinitionPersistencePort.deleteProduct(productId, restaurantId);
    }

    @Override
    public void deleteAdding(final Long addingId, final Long restaurantId) throws AddingException {

        productDefinitionPersistencePort.deleteAdding(addingId, restaurantId);
    }

    @Override
    public Page<Product> retrieveRestaurantProducts(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws ProductException {

        return productDefinitionPersistencePort.retrieveRestaurantProducts(restaurantId, pageNumber, pageSize);
    }

    @Override
    public Page<Adding> retrieveProductAddings(final Long restaurantId, final Long productId, final Integer pageNumber, final Integer pageSize) throws ProductException {

        return productDefinitionPersistencePort.retrieveProductAddings(restaurantId, productId, pageNumber, pageSize);
    }

    @Override
    public Page<Adding> retrieveRestaurantAddings(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws ProductException {

        return productDefinitionPersistencePort.retrieveRestaurantAddings(restaurantId, pageNumber, pageSize);
    }
}
