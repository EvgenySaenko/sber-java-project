package com.evgenys.online.shop.controllers;


import com.evgenys.online.shop.dto.OrderDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Order;
import com.evgenys.online.shop.services.CartService;
import com.evgenys.online.shop.services.OrderService;
import com.evgenys.online.shop.utils.converter.OrderConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final CartService cartService;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(Principal principal, @RequestParam UUID cartId, @RequestParam String address){
       Order order = orderService.saveOrder(principal.getName(), cartId, address);
       cartService.clearCart(cartId);
       return orderConverter.convertToOrderDto(order);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id){
        Order order =  orderService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        return orderConverter.convertToOrderDto(order);
    }

    @GetMapping
    public List<OrderDto> getOrdersCurrentUser(Principal principal){
        return orderService.findAllOrdersForCurrentUser(principal.getName());
    }

}
