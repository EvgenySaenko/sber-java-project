package com.evgenys.online.shop.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

class OrdersControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    void getOrdersCurrentUser() throws Exception {
        String jsonRequest = "{\n" +
                "\t\"username\": \"bob\",\n" +
                "\t\"password\": \"100\"\n" +
                "}";

        MvcResult result = mockMvc.perform(post("/auth")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String token = result.getResponse().getContentAsString();
        token =  token.replace("{\"token\":\"","").replace("\"}","");
        mockMvc.perform(get("/api/v1/orders").header("Authorization","Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }
}