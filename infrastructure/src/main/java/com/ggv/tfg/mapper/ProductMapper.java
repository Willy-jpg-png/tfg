package com.ggv.tfg.mapper;

import com.ggv.tfg.model.Adding;
import com.ggv.tfg.model.Product;
import com.ggv.tfg.model.Restaurant;
import com.ggv.tfg.openapi.rest.*;
import com.ggv.tfg.persistence.sqlserver.entity.AddingDao;
import com.ggv.tfg.persistence.sqlserver.entity.ProductDao;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true))
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    ProductDao toDao(Product product, List<AddingDao> addingDaos);

    @Mapping(target = "id", ignore = true)
    AddingDao toDao(Adding adding);

    AddingDao toDao(Adding adding, Long addingId);

    @Mapping(target = "name", source = "productCreationRest.name")
    @Mapping(target = "description", source = "productCreationRest.description")
    @Mapping(target = "id", ignore = true)
    Product toDomain(ProductCreationRest productCreationRest, Restaurant restaurant);

    @Mapping(target = "name", source = "addingCreationRest.name")
    @Mapping(target = "description", source = "addingCreationRest.description")
    @Mapping(target = "id", ignore = true)
    Adding toDomain(AddingCreationRest addingCreationRest, Restaurant restaurant);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addings", source = "adding")
    Product toDomain(ProductUpdateRest productUpdateRest, List<Adding> adding);

    @Mapping(target = "addings", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toDomain(ProductUpdateRest productUpdateRest);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    Adding toDomain(AddingUpdateRest addingUpdateRest);

    PagedProductRest toPagedProductRest(Page<Product> productPage);

    @Mapping(target = "restaurant", source = "restaurant")
    Product toDomain(ProductDao productDao);

    PagedAddingRest toPagedAddingRest(Page<Adding> productPage);

    Adding toDomain(AddingDao addingDao);

    ProductDao toDao(Product product);
}
