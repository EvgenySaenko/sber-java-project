package com.evgenys.online.shop.controllers;


import com.evgenys.online.shop.beans.Cart;
import com.evgenys.online.shop.dto.OrderDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Order;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.services.OrderService;
import com.evgenys.online.shop.services.UserService;
import com.evgenys.online.shop.utils.converter.OrderConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final UserService userService;
    private final OrderService orderService;
    private final Cart cart;
    private final OrderConverter orderConverter;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(Principal principal, @RequestParam String address){
        User user = userService.findByUsername(principal.getName()).orElseThrow(()->new ResourceNotFoundException("User with this name does not exist"));
        Order order =  new Order(user,cart, user.getEmail(), address);
        order = orderService.saveOrder(order);
        cart.clear();
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
