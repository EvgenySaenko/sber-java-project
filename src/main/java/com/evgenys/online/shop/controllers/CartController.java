package com.evgenys.online.shop.controllers;

import com.evgenys.online.shop.dto.CartDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Cart;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.services.CartService;
import com.evgenys.online.shop.services.UserService;
import com.evgenys.online.shop.utils.converter.CartConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter converter;


    @PostMapping
    public UUID createNewCart(Principal principal){//клиент может создать корзину, и сохранить себе в localStorage полученый id
        if (principal == null) {
            return cartService.getCartForUser(null,null);

        }
        return cartService.getCartForUser(principal.getName(),null);

    }

    @GetMapping("/{uuid}")//запросить корзину
    public CartDto getCurrentCart(@PathVariable(name = "uuid") UUID cartId){
        return cartService.findById(cartId).map(converter::convertToCartDto).orElseThrow(()-> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
    }

    @PostMapping("/add")//добавить продукт в корзину
    public void addToCart(@RequestParam UUID cartId, @RequestParam(name = "product_id") Long productId) {
       cartService.addToCart(cartId,productId);
    }

    @PostMapping("/clear")//очистить корзину
    public void clearCart(@RequestParam (name = "uuid") UUID cartId) {
        cartService.clearCart(cartId);
    }

}
