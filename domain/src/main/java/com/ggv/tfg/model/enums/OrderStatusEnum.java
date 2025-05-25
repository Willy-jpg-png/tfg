package com.ggv.tfg.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    ON_PREPARATION,
    ON_DELIVERY,
    DELIVERED;
}
