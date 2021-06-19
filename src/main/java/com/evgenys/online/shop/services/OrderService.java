package com.evgenys.online.shop.services;


import com.evgenys.online.shop.beans.Cart;
import com.evgenys.online.shop.dto.OrderDto;
import com.evgenys.online.shop.persistence.entities.Order;
import com.evgenys.online.shop.persistence.entities.User;
import com.evgenys.online.shop.repositories.OrderRepository;
import com.evgenys.online.shop.utils.converter.OrderConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final Cart cart;


    public Order saveOrder(Order order) {//сохраняя ордер-> сохраняем и ордер айтемы(включенно каскадирование)
        return orderRepository.save(order);

    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderDto> findAllOrdersForCurrentUser(String username){
        return orderRepository.findOrderByUserUsername(username).stream().map(orderConverter::convertToOrderDto).collect(Collectors.toList());
    }

}
