package com.evgenys.online.shop.services;

import com.evgenys.online.shop.dto.ProductDto;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.repositories.ProductRepository;
import com.evgenys.online.shop.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final Converter converter;
    private final ProductRepository productRepository;

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public List<ProductDto> findAll(){
        return productRepository.findAll().stream().map(converter::convertToProductDto).collect(Collectors.toList());
    }

    public Page<ProductDto> findAll(int page){
        //вернет страницу продуктов,PageRequest.of(так создается Pageable)
        //мы не достаем из базы все продукты а достаем только те что нужны для отображения на странице(например 5 штук)
        Page<Product> originalPage = productRepository.findAll(PageRequest.of(page - 1, 5));
        return new PageImpl<>(originalPage.getContent().stream().map(converter::convertToProductDto).collect(Collectors.toList()),
                                                  originalPage.getPageable(), originalPage.getTotalElements());
    }

    public Product saveOrUpdate(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}
