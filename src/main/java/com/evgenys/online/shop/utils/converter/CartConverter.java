package com.evgenys.online.shop.utils.converter;


import com.evgenys.online.shop.beans.Cart;
import com.evgenys.online.shop.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final OrderItemsConverter orderItemsConverter;

    public CartDto convertToCartDto(Cart cart){
        return CartDto.builder()
                .totalPrice(cart.getTotalPrice())
                .items(cart.getItems().stream().map(orderItemsConverter::convertToOrderItemDto).collect(Collectors.toList()))
        .build();
    }

}
