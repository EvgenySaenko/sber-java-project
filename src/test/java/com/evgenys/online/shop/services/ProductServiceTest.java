package com.evgenys.online.shop.services;

import com.evgenys.online.shop.dto.ProductDto;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.repositories.ProductRepository;
import com.evgenys.online.shop.utils.converter.ProductConverter;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

//так как мы хотим поднять только сервис, то если в сервисе еще есть зависимости их нужно тоже указать, чтобы спринг закинул их в контекст)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductService.class, ProductConverter.class})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductConverter converter;


    @MockBean
    private ProductRepository mockProductRepository;

    @Before
    public void setUp(){
        ProductDto productDto = ProductDto.builder()
                .title("Кофе")
                .price(new BigDecimal(200))
        .build();
        productService.saveOrUpdate(converter.convertToProduct(productDto));
    }

    @Test
    void findById() {
        Product demoProduct =  new Product();
        demoProduct.setId(10L);
        demoProduct.setTitle("Зефир");
        demoProduct.setPrice(new BigDecimal(150));
        demoProduct.setCreatedAt(LocalDateTime.now());
        demoProduct.setUpdatedAt(LocalDateTime.now());

        Mockito
                .doReturn(Optional.of(demoProduct))
                .when(mockProductRepository)
                .findById(10L);


        Product product = productService.findById(10L).get();
        Mockito.verify(mockProductRepository,Mockito.times(1)).findById(ArgumentMatchers.eq(10L));
        Assertions.assertEquals("Зефир",product.getTitle());
    }

    @Test
    public void saveOrUpdate(){
        Assertions.assertEquals(1,productService.findAll(null,1,5).getSize());
    }


}