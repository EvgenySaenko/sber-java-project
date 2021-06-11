package com.evgenys.online.shop.controllers;

import com.evgenys.online.shop.beans.Cart;
import com.evgenys.online.shop.dto.CartDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.services.CartService;
import com.evgenys.online.shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final Cart cart;

    @GetMapping
    public CartDto getCart() {
        return cartService.getCartDto();
    }


    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cart.add(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cart.clear();
    }
}
