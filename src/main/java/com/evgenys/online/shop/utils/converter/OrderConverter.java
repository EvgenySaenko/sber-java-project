package com.evgenys.online.shop.utils.converter;

import com.evgenys.online.shop.dto.DeliveryOrderDto;
import com.evgenys.online.shop.dto.OrderDto;
import com.evgenys.online.shop.persistence.entities.Order;
import com.evgenys.online.shop.persistence.entities.StorePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemsConverter orderItemsConverter;

    private DateTimeFormatter formatter;

    @PostConstruct
    public void init(){
        this.formatter = DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy");

    }
    public OrderDto convertToOrderDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
                .address(order.getAddress())
                .totalPrice(BigDecimal.valueOf(order.getPrice().doubleValue()))
                .creationDateTime(formatter.format(order.getCreatedAt()))
                .items(order.getItems().stream().map(orderItemsConverter::convertToOrderItemDto).collect(Collectors.toList()))
        .build();
    }

    public DeliveryOrderDto convertToDeliveryOrderDto(Order order, StorePoint storePoint){
        return DeliveryOrderDto.builder()
                .id(order.getId())
                .firstName(order.getUser().getFirstName())
                .addressFrom(storePoint.getCity() + " " + storePoint.getStreet() + " " + storePoint.getHouseNumber())
                .addressTo(order.getAddress())
                .totalPrice(BigDecimal.valueOf(order.getPrice().doubleValue()))
                .creationDateTime(formatter.format(order.getCreatedAt()))
                .build();
    }
}
