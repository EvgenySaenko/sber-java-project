package com.evgenys.online.shop.controllers;

import com.evgenys.online.shop.dto.ProductDto;
import com.evgenys.online.shop.dto.UserDtoRegistration;
import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.services.ProductService;
import com.evgenys.online.shop.utils.converter.ProductConverter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest()
class AdminControllerTest {

    @Test
    void findAllProducts() {

    }

    @Test
    void findProductById() {
    }

    @Test
    @WithMockUser(username = "john",password = "100",roles = "ADMIN")
    void saveNewProduct() {
        ProductDto productDto = ProductDto.builder()
                .title("Вишня")
                .price(new BigDecimal(150))
       .build();

        Product product = given()
                .baseUri("http://localhost:8189/shop")
                .basePath("/api/v1/admin")
                .contentType(ContentType.JSON)
                .body(productDto)
                .when().post()
                .then().extract().as(Product.class);

        assertThat(product).isNotNull().extracting(Product::getTitle)
                .isEqualTo(productDto.getTitle());





    }

    @Test
    void editProduct() {
    }

    @Test
    void deleteProduct() {
    }
}