package com.evgenys.online.shop.services;


import com.evgenys.online.shop.dto.OrderDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Cart;
import com.evgenys.online.shop.persistence.entities.Order;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.repositories.OrderRepository;
import com.evgenys.online.shop.utils.converter.OrderConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final OrderConverter orderConverter;

    @Transactional
    public Order saveOrder(String username, UUID cartId,String address) {//сохраняя ордер-> сохраняем и ордер айтемы(включенно каскадирование)
        User user = userService.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User with this name does not exist"));
        Cart cart = cartService.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Unable to find cart with id: " + cartId));
        Order order =  new Order(user,cart, user.getEmail(), address);
        return orderRepository.save(order);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderDto> findAllOrdersForCurrentUser(String username){
        return orderRepository.findOrderByUserUsername(username).stream().map(orderConverter::convertToOrderDto).collect(Collectors.toList());
    }

}
