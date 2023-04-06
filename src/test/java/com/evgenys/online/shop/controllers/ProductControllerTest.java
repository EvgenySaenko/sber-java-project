package com.evgenys.online.shop.controllers;

import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.services.ProductService;
import com.evgenys.online.shop.utils.specifications.ProductSpecifications;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productServiceMock;

//    @Before
//    public void setUp(){
//        List<Product> products = new ArrayList<>();
//        products.add(new Product(1L,"Яблочный сок",new BigDecimal(140), LocalDateTime.now(),LocalDateTime.now()));
//        products.add(new Product(2L,"Фруктовый йогурт ",new BigDecimal(100), LocalDateTime.now(),LocalDateTime.now()));
//        products.add(new Product(3L,"Кефир",new BigDecimal(70), LocalDateTime.now(),LocalDateTime.now()));
//        products.add(new Product(4L,"Пряники",new BigDecimal(110), LocalDateTime.now(),LocalDateTime.now()));
//    }


    @Test
    void findAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content.title[0]",is("Bread")))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void findAllProductsRestAssured() throws Exception {
       List<Product> products = given()
               .baseUri("http://localhost:8189/shop")
               .basePath("/api/v1/products")
               .contentType(ContentType.JSON)
               .when()
               .log().all()
               .get()
               .then()
               .log().all()
               .statusCode(200)
               .extract().jsonPath().getList("content", Product.class);
       assertThat(products).extracting(Product::getTitle).contains("Hamburger");
       assertEquals(5,products.size());
              // .body("content.find{it.price=='24'}.price", equalTo("Bread"));
    }

    @Test
    void findProductById() throws Exception  {
        mockMvc.perform(get("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.title[0]",is("Bread")));
    }
}