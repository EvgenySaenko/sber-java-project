package com.evgenys.online.shop.utils.converter;

import com.evgenys.online.shop.dto.OrderItemDto;
import com.evgenys.online.shop.persistence.entities.OrderItem;
import com.evgenys.online.shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrderItemsConverter {

    public OrderItemDto convertToOrderItemDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .productTitle(orderItem.getProduct().getTitle())
                .quantity(orderItem.getQuantity())
                .pricePerProduct(orderItem.getPricePerProduct())
                .price(orderItem.getPrice())
                .build();
    }
}
