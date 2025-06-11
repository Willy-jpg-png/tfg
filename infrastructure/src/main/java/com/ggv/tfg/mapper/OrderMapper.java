package com.ggv.tfg.mapper;

import com.ggv.tfg.model.*;
import com.ggv.tfg.model.enums.OrderStatusEnum;
import com.ggv.tfg.openapi.rest.*;
import com.ggv.tfg.persistence.sqlserver.entity.*;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, builder = @Builder(disableBuilder = true), uses = {UserMapper.class, ProductMapper.class})
public interface OrderMapper {

    @Mapping(target = "orderProducts", source = "products")
    @Mapping(target = "restaurant", source = "order.restaurant")
    @Mapping(target = "customer", source = "order.customer")
    @Mapping(target = "deliveryPerson", source = "order.deliveryPerson")
    OrderDao toDao(Order order, List<OrderProductDao> products);

    @Mapping(target = "orderProducts", source = "products")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveryPerson", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    Order toDomain(OrderCreationRest orderCreationRest, Customer customer, Restaurant restaurant, List<Product> products);

    Order toDomain(OrderDao orderDao);

    @Mapping(target = "products", source = "orderProducts")
    OrderRest toRest(Order order);

    PagedOrderRest toPagedOrderRest(Page<Order> orderPage);

    default OrderStatusEnum toOrderStatusEnum(final OrderStatusUpdateRequest request) {
        if (request == null || request.getOrderStatus() == null) return null;
        return OrderStatusEnum.valueOf(request.getOrderStatus().getValue());
    }

    OrderProduct toDomain(OrderProductDao dao);

    @Mapping(target = "order", ignore = true)
    OrderProductDao toDao(OrderProduct orderProduct);

    OrderDao toDao(Order order);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "addings", source = "addings")
    ProductRest1 toProductRest(OrderProduct orderProduct);

}
