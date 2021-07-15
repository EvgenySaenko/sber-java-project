package com.evgenys.online.shop.repositories;

import com.evgenys.online.shop.persistence.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAll(){
        List<Product> products = productRepository.findAll();
        Assertions.assertEquals(23,products.size());
    }

    @Test
    public void findById(){
        Product product = productRepository.findById(1L).get();
        Assertions.assertNotNull(product);
        Assertions.assertEquals("Bread",product.getTitle());
    }
}