package com.evgenys.online.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderDto implements Serializable {
    private Long id;
    private String firstName;
    private String addressFrom;
    private String addressTo;
    private BigDecimal totalPrice;
    private String creationDateTime;
}
