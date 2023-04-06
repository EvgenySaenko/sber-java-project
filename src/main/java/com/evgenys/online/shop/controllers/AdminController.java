package com.evgenys.online.shop.controllers;

import com.evgenys.online.shop.dto.ProductDto;
import com.evgenys.online.shop.exceptions.ResourceNotFoundException;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.services.ProductService;
import com.evgenys.online.shop.utils.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Page<ProductDto> findAllProducts(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "p", defaultValue = "1") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(ProductSpecifications.build(params), page, 5);
    }


    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id){
        return productService.findProductDtoById(id).orElseThrow(()-> new ResourceNotFoundException("Product with id: " + id + " does not exist"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveNewProduct(@RequestBody Product product){
        product.setId(null);
        return productService.saveOrUpdate(product);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Product editProduct(@RequestBody Product product) {
        if (product.getId() == null || !productService.existsById(product.getId())) {
            throw new ResourceNotFoundException("Product with id: " + product.getId() + " does not exist");
        }
        if (product.getPrice().doubleValue() < 0.0) {
            throw new ResourceNotFoundException("Product's price can not be negative");
        }
        return productService.saveOrUpdate(product);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
