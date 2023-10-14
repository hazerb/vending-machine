package com.casestudy.controller;

import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.dto.AddProductsRequest;
import com.casestudy.dto.LoginRequest;
import com.casestudy.dto.ProductDto;
import com.casestudy.dto.UpdatePriceRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.casestudy.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    public String validToken;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void loginAndGetToken() throws Exception {
        LoginRequest requestObject = new LoginRequest(VendingMachineConstants.ADMIN_UUID);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestObject);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andReturn();

        validToken = result.getResponse().getHeader("Authorization");
    }


    @Test
    void WhenUuidCorrectThenLoginSuccessful() throws Exception {
        LoginRequest requestObject = new LoginRequest(VendingMachineConstants.ADMIN_UUID);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"));
    }

    @Test
    void WhenUuidNotCorrectThenLoginNotSuccessful() throws Exception {
        LoginRequest requestObject = new LoginRequest(randomUuid);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(401));
    }

    @Test
    void WhenTokenCorrectThenResetsVendingMachine() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/reset")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void WhenTokenNotCorrectThenNotResetsVendingMachine() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", randomToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/reset")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(401));
    }

    @Test
    void WhenTokenCorrectThenCollectsMoney() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/collect-money")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void WhenTokenNotCorrectThenNotCollectsMoney() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", randomToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/collect-money")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void WhenTokenCorrectAddsProduct() throws Exception {
        AddProductsRequest requestObject = new AddProductsRequest();
        List<ProductDto> productDtos = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        productDto.setName(productName);
        productDtos.add(productDto);
        requestObject.setProductDtos(productDtos);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestObject);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/add-products")
                        .headers(headers)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void WhenTokenNotCorrectThenNotAddsProduct() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", randomToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/add-products")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    void WhenTokenCorrectThenUpdatesPrice() throws Exception {
        UpdatePriceRequest requestObject = new UpdatePriceRequest();
        requestObject.setName("Water");
        requestObject.setPrice(10);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(requestObject);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/update-price")
                        .headers(headers)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void WhenTokenNotCorrectThenNotUpdatePrice() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", randomToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/update-price")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(400));
    }

    @Test
    void WhenTokenCorrectThenMachineIsFetched() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/vending-machine")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonObject = objectMapper.readTree(responseContent);

        assertTrue(jsonObject.isObject(), "The response contains a JSON object");
    }

    @Test
    void WhenTokenNotCorrectThenMachineIsNotFetched() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", randomToken);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/vending-machine")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(401));
    }

}