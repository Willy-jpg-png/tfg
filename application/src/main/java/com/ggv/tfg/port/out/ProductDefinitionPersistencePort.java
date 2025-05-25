package com.ggv.tfg.port.out;

import com.ggv.tfg.exception.AddingException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.model.Adding;
import com.ggv.tfg.model.Product;
import org.springframework.data.domain.Page;

public interface ProductDefinitionPersistencePort {

    void createProduct(Product product) throws ProductException, AddingException;

    void createAdding(Adding adding) throws ProductException;

    void updateProduct(Product product, Long productId, Long restaurantId) throws ProductException;

    void updateProductAddings(Product product, Long productId, Long restaurantId) throws ProductException, AddingException;

    void updateAdding(Adding adding, Long addingId, Long restaurantId) throws AddingException;

    void deleteProduct(Long productId, Long restaurantId) throws ProductException;

    void deleteAdding(Long addingId, Long restaurantId) throws AddingException;

    Page<Product> retrieveRestaurantProducts(Long restaurantId, Integer pageNumber, Integer pageSize) throws ProductException;

    Page<Adding> retrieveProductAddings(Long restaurantId, Long productId, Integer pageNumber, Integer pageSize) throws ProductException;

    Page<Adding> retrieveRestaurantAddings(Long restaurantId, Integer pageNumber, Integer pageSize) throws ProductException;

}
