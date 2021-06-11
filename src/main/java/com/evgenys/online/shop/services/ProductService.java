package com.evgenys.online.shop.services;

import com.evgenys.online.shop.dto.ProductDto;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.repositories.ProductRepository;
import com.evgenys.online.shop.utils.converter.ProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductConverter converter;
    private final ProductRepository productRepository;

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public Optional<ProductDto> findProductDtoById(Long id){
        return productRepository.findById(id).map(converter::convertToProductDto);
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int pageSize){
        //PageRequest.of(так создается Pageable)
        //мы не достаем из базы все продукты а достаем только те что нужны для отображения на странице(например 5 штук)
        Page<Product> originalPage = productRepository.findAll(spec,PageRequest.of(page - 1, pageSize));
        return originalPage.map(converter::convertToProductDto);
    }

    public Product saveOrUpdate(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}
