package com.evgenys.online.shop.controllers;


import com.evgenys.online.shop.dto.ProductDto;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> findAllProducts(@RequestParam(name = "min_price",defaultValue = "0")Integer minPrice,
                                            @RequestParam(name = "max_price",required = false)Integer maxPrice,
                                            @RequestParam(name = "title",required = false)String title,
                                            @RequestParam(name = "page",defaultValue = "1")Integer page)
    {
        if (page < 1){
           page = 1;
        }
        return productService.findAll(page);//из Page выдергиваем список продуктов getContent()
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return productService.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveNewProduct(@RequestBody Product product){
        product.setId(null);
        return productService.saveOrUpdate(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product){
        return productService.saveOrUpdate(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
