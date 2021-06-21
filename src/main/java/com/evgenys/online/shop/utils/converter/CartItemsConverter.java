package com.evgenys.online.shop.utils.converter;

import com.evgenys.online.shop.dto.CartItemDto;
import com.evgenys.online.shop.persistence.entities.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemsConverter {

    public CartItemDto convertToCartItemDto(CartItem cartItem){
        return CartItemDto.builder()
                .id(cartItem.getId())
                .productTitle(cartItem.getProduct().getTitle())
                .quantity(cartItem.getQuantity())
                .pricePerProduct(cartItem.getPricePerProduct())
                .price(cartItem.getPrice())
                .build();
    }
}
