package com.casestudy.controller;

import com.casestudy.dto.MoneyDto;
import com.casestudy.dto.ProductRequest;
import com.casestudy.utility.RandomUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.casestudy.TestConstants.correctAmount;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RandomUtil randomUtil;


    @Test
    void insertsMoney() throws Exception {
        MoneyDto requestObject = new MoneyDto();
        requestObject.setAmount(correctAmount);

        when(randomUtil.isFake()).thenReturn(false);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/insert-money")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
//if product quantity bigger than zero in database, it works. if there is not it gives 404 error.
    void buysProduct() throws Exception {
        ProductRequest requestObject = new ProductRequest();
        requestObject.setName("Water");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestObject);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/buy-product")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JsonNode jsonObject = objectMapper.readTree(responseContent);

        assertTrue(jsonObject.isObject(), "The response contains a JSON object");
    }

    @Test
    void cancelsRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cancel-request")
                )
                .andExpect(status().isOk());
    }

    @Test
    void getsProducts() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/products")
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonObject = objectMapper.readTree(responseContent);

        assertTrue(jsonObject.isObject(), "The response contains a JSON object");
    }

    @Test
    void getMachine() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/vending-machine")
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonObject = objectMapper.readTree(responseContent);

        assertTrue(jsonObject.isObject(), "The response contains a JSON object");
    }

}