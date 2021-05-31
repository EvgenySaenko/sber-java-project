package com.evgenys.online.shop.utils;

import com.evgenys.online.shop.dto.ProductDto;
import com.evgenys.online.shop.persistence.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Converter {

    public Product convertToProduct(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
        .build();
    }

    public ProductDto convertToProductDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
        .build();
    }
}
