package com.evgenys.online.shop.utils.converter;


import com.evgenys.online.shop.dto.CartDto;
import com.evgenys.online.shop.persistence.entities.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemsConverter cartItemsConverter;

    public CartDto convertToCartDto(Cart cart){
        return CartDto.builder()
                .totalPrice(cart.getPrice())
                .items(cart.getItems().stream().map(cartItemsConverter::convertToCartItemDto).collect(Collectors.toList()))
        .build();
    }

}
