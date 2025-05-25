package com.ggv.tfg.adapter.driven;

import com.ggv.tfg.exception.AddingException;
import com.ggv.tfg.exception.ProductException;
import com.ggv.tfg.exception.ExceptionMessage;
import com.ggv.tfg.mapper.ProductMapper;
import com.ggv.tfg.model.Adding;
import com.ggv.tfg.model.Product;
import com.ggv.tfg.persistence.sqlserver.entity.AddingDao;
import com.ggv.tfg.persistence.sqlserver.entity.ProductDao;
import com.ggv.tfg.persistence.sqlserver.repository.AddingRepository;
import com.ggv.tfg.persistence.sqlserver.repository.ProductRepository;
import com.ggv.tfg.port.out.ProductDefinitionPersistencePort;
import com.ggv.tfg.stereotype.Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Adapter
@Slf4j
@Transactional
public class ProductDefinitionPersistenceAdapter implements ProductDefinitionPersistencePort {

    private final ProductRepository productRepository;
    private final AddingRepository addingRepository;
    private final ProductMapper productMapper;

    @Override
    public void createProduct(final Product product) throws ProductException, AddingException {

        final List<AddingDao> addingList = new ArrayList<>();
        for (final Long addingId : product.getAddingIds()) {
            final var addingDao = addingRepository.findById(addingId);
            if (addingDao.isEmpty())
                throw new AddingException(ExceptionMessage.ADDING_NOT_FOUND_EXCEPTION.getMessage());
            addingList.add(addingDao.get());
        }
        final ProductDao productDao = productMapper.toDao(product, addingList);

        try {
            productRepository.save(productDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new ProductException(ExceptionMessage.CREATE_PRODUCT_EXCEPTION.getMessage());
        }
    }

    @Override
    public void createAdding(final Adding adding) throws ProductException {

        final AddingDao addingDao = productMapper.toDao(adding);

        try {
            addingRepository.save(addingDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new ProductException(ExceptionMessage.CREATE_PRODUCT_EXCEPTION.getMessage());
        }
    }

    @Override
    public void updateProduct(final Product product, final Long productId, final Long restaurantId) throws ProductException {

        if (!productRepository.existsByIdAndRestaurantId(productId, restaurantId))
            throw new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage());

        final ProductDao existingDao = productRepository.findById(productId).
                orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_UPDATE_EXCEPTION.getMessage()));

        if (product.getName() != null)
            existingDao.setName(product.getName());
        if (product.getDescription() != null)
            existingDao.setDescription(product.getDescription());
        if (product.getPrice() != null)
            existingDao.setPrice(product.getPrice());
        if (product.getImage() != null)
            existingDao.setImage(product.getImage());

        try {
            productRepository.save(existingDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new ProductException(ExceptionMessage.PRODUCT_UPDATE_EXCEPTION.getMessage());
        }
    }

    @Override
    public void updateProductAddings(final Product product, final Long productId, final Long restaurantId) throws ProductException, AddingException {

        if (!productRepository.existsByIdAndRestaurantId(productId, restaurantId))
            throw new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage());

        final ProductDao existingDao = productRepository.findById(productId).
                orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_UPDATE_EXCEPTION.getMessage()));

        final List<AddingDao> addingList = new ArrayList<>();
        for (final Long addingId : product.getAddingIds()) {
            final AddingDao addingDao = addingRepository.findById(addingId).
                    orElseThrow(() -> new AddingException(ExceptionMessage.ADDING_NOT_FOUND_EXCEPTION.getMessage()));
            addingList.add(addingDao);

        }

        existingDao.setAddings(addingList);

        try {
            productRepository.save(existingDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new ProductException(ExceptionMessage.PRODUCT_UPDATE_EXCEPTION.getMessage());
        }
    }

    @Override
    public void updateAdding(final Adding adding, final Long addingId, final Long restaurantId) throws AddingException {

        if (!addingRepository.existsByIdAndRestaurantId(addingId, restaurantId))
            throw new AddingException(ExceptionMessage.ADDING_NOT_FOUND_EXCEPTION.getMessage());

        final AddingDao existingDao = addingRepository.findById(addingId).
                orElseThrow(() -> new AddingException(ExceptionMessage.PRODUCT_UPDATE_EXCEPTION.getMessage()));

        if (adding.getName() != null)
            existingDao.setName(adding.getName());
        if (adding.getDescription() != null)
            existingDao.setDescription(adding.getDescription());
        if (adding.getPrice() != null)
            existingDao.setPrice(adding.getPrice());
        if (adding.getImage() != null)
            existingDao.setImage(adding.getImage());

        try {
            addingRepository.save(existingDao);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new AddingException(ExceptionMessage.ADDING_UPDATE_EXCEPTION.getMessage());
        }
    }

    @Override
    public void deleteProduct(final Long productId, final Long restaurantId) throws ProductException {

        if (!productRepository.existsByIdAndRestaurantId(productId, restaurantId))
            throw new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage());

        try {
            productRepository.deleteById(productId);
        } catch (final Exception e) {
            throw new ProductException(ExceptionMessage.PRODUCT_DELETE_EXCEPTION.getMessage());
        }
    }

    @Override
    public void deleteAdding(final Long addingId, final Long restaurantId) throws AddingException {

        if (!addingRepository.existsByIdAndRestaurantId(addingId, restaurantId))
            throw new AddingException(ExceptionMessage.ADDING_NOT_FOUND_EXCEPTION.getMessage());

        try {
            addingRepository.deleteById(addingId);
        } catch (final Exception e) {
            throw new AddingException(ExceptionMessage.ADDING_DELETE_EXCEPTION.getMessage());
        }
    }

    @Override
    public Page<Product> retrieveRestaurantProducts(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws ProductException {

        final var pageRequest = PageRequest.of(pageNumber, pageSize);
        final var productPage = productRepository.findAllByRestaurantId(restaurantId, pageRequest);
        return productPage.map(productMapper::toDomain);
    }

    @Override
    public Page<Adding> retrieveProductAddings(final Long restaurantId, final Long productId, final Integer pageNumber, final Integer pageSize) throws ProductException {
        final ProductDao product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND_EXCEPTION.getMessage()));

        if (!product.getRestaurant().getId().equals(restaurantId)) {
            throw new ProductException(ExceptionMessage.RESTAURANT_NOT_FOUND_EXCEPTION.getMessage());
        }

        final List<AddingDao> allAddings = product.getAddings();

        final int start = pageNumber * pageSize;
        final int end = Math.min(start + pageSize, allAddings.size());

        if (start >= allAddings.size()) {
            return Page.empty(PageRequest.of(pageNumber, pageSize));
        }

        final List<Adding> pageContent = allAddings.subList(start, end)
                .stream()
                .map(productMapper::toDomain)
                .toList();

        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), allAddings.size());
    }

    @Override
    public Page<Adding> retrieveRestaurantAddings(final Long restaurantId, final Integer pageNumber, final Integer pageSize) throws ProductException {

        final var pageRequest = PageRequest.of(pageNumber, pageSize);
        final var addingPage = addingRepository.findAllByRestaurantId(restaurantId, pageRequest);
        return addingPage.map(productMapper::toDomain);
    }

}
