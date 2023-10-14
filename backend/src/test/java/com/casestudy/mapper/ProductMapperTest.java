package com.casestudy.mapper;

import com.casestudy.dto.AddProductsResponse;
import com.casestudy.dto.GetProductsResponse;
import com.casestudy.dto.ProductDto;
import com.casestudy.dto.ProductResponse;
import com.casestudy.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.casestudy.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void productResponseIsGenerated() {
        int change = randomAmount;
        int quantity = randomAmount;

        ProductResponse productResponse = productMapper.generateProductResponse(change, quantity);
        assertEquals(randomAmount, productResponse.getProductQuantity());
        assertEquals(randomAmount, productResponse.getProductQuantity());
    }

    @Test
    void getProductResponseIsGenerated() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName(productName);
        products.add(product);


        GetProductsResponse getProductsResponse = productMapper.generateGetProductsResponse(products);
        ProductDto productDto = getProductsResponse.getProductDtos().get(0);
        assertEquals(product.getName(), productDto.getName());
    }

    @Test
    void addProductsResponseIsGenerated() {
        AddProductsResponse addProductsResponse = productMapper.generateAddProductsResponse(defaultAmount, randomAmount);
        assertEquals(defaultAmount, addProductsResponse.getRealProductCount());
        assertEquals(randomAmount, addProductsResponse.getFakeProductCount());
    }

}