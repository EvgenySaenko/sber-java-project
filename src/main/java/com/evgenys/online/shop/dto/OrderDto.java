package com.evgenys.online.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
  private Long id;
  private String username;
  private String address;
  private BigDecimal totalPrice;
  private String creationDateTime;
  private List<OrderItemDto> items;
}
