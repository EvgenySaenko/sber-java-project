package com.evgenys.online.shop.utils.converter;

import com.evgenys.online.shop.dto.OrderDto;
import com.evgenys.online.shop.persistence.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private DateTimeFormatter formatter;

    @PostConstruct
    public void init(){
        this.formatter = DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy");

    }
//LocalDateTime.parse(order.getCreatedAt().toString(),formatter).toString()
    public OrderDto convertToOrderDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
                .address(order.getAddress())
                .totalPrice(BigDecimal.valueOf(order.getPrice().doubleValue()))
                .creationDateTime(formatter.format(order.getCreatedAt()))
                .build();
    }
}
