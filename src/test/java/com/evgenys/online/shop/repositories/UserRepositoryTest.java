package com.evgenys.online.shop.repositories;

import com.evgenys.online.shop.persistence.entities.Product;
import com.evgenys.online.shop.persistence.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;


    @Test
    void findByUsername() {
        User user = userRepository.findByUsername("bob").get();
        Assertions.assertEquals("Bobby",user.getFirstName());
        Assertions.assertEquals("Fischer",user.getLastName());
        Assertions.assertEquals("bob@gmail.com",user.getEmail());
        Assertions.assertEquals("8-988-555-35-35",user.getPhone());
    }

    @Test
    void findByUsernameAndPhoneAndEmail() {
        User user = userRepository.findByUsernameAndPhoneAndEmail("don","8-977-222-44-44","don@gmail.com").get();
        Assertions.assertEquals("don",user.getUsername());
        Assertions.assertEquals("don@gmail.com",user.getEmail());
        Assertions.assertEquals("8-977-222-44-44",user.getPhone());
    }

    @Test
    void findByActivationCode() {

    }
}