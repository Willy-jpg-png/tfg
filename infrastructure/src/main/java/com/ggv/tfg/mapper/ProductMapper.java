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
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true))
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    ProductDao toDao(Product product, List<AddingDao> addings);

    @Mapping(target = "id", ignore = true)
    AddingDao toDao(Adding adding);

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
    Product toDomain(ProductUpdateRest productUpdateRest);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "id", ignore = true)
    Adding toDomain(AddingUpdateRest addingUpdateRest);

    PagedProductRest toPagedProductRest(Page<Product> productPage);

    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "addingIds", source = "addings", qualifiedByName = "mapAddingsToIds")
    Product toDomain(ProductDao productDao);

    @Named("mapAddingsToIds")
    static List<Long> mapAddingsToIds(List<com.ggv.tfg.persistence.sqlserver.entity.AddingDao> addings) {
        if (addings == null) return null;
        return addings.stream().map(com.ggv.tfg.persistence.sqlserver.entity.AddingDao::getId).collect(Collectors.toList());
    }

    PagedAddingRest toPagedAddingRest(Page<Adding> productPage);

    Adding toDomain(AddingDao addingDao);

}
