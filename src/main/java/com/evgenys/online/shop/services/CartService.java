package com.evgenys.online.shop.services;


import com.evgenys.online.shop.beans.Cart;
import com.evgenys.online.shop.dto.CartDto;
import com.evgenys.online.shop.utils.converter.CartConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final Cart cart;
    private final CartConverter converter;

    public CartDto getCartDto(){
        return converter.convertToCartDto(cart);
    }
}
