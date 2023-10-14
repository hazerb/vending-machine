package com.casestudy.service;

import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.dto.ProductDto;
import com.casestudy.dto.ProductRequest;
import com.casestudy.dto.ProductResponse;
import com.casestudy.dto.UpdatePriceRequest;
import com.casestudy.exception.BadRequestRestException;
import com.casestudy.exception.NotFoundRestException;
import com.casestudy.mapper.ProductMapper;
import com.casestudy.model.Product;
import com.casestudy.model.VendingMachine;
import com.casestudy.repository.ProductRepository;
import com.casestudy.repository.VendingMachineRepository;
import com.casestudy.service.impl.ProductServiceImpl;
import com.casestudy.utility.RandomUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.casestudy.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private VendingMachineRepository vendingMachineRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @MockBean
    private RandomUtil randomUtil;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void GivenProductIsAvailableWhenInsertedMoneyIsEnoughThenProductIsBought() {
        Product product = new Product();
        product.setName(productName);
        product.setQuantity(1);
        product.setPrice(randomAmount);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(productName);

        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(randomAmount);

        when(vendingMachineRepository.findById(any())).thenReturn(Optional.of(vendingMachine));
        when(productRepository.findByName(any())).thenReturn(product);
        when(productMapper.generateProductResponse(anyInt(), anyInt())).thenReturn(new ProductResponse());

        productService.buyProduct(productRequest);

        assertEquals(0, product.getQuantity());
        assertEquals(VendingMachineConstants.DEFAULT_INSERTED_MONEY, vendingMachine.getInsertedMoney());
        assertEquals(randomAmount + vendingMachine.getInsertedMoney(), vendingMachine.getBalance());

    }

    @Test
    void WhenProductIsNotAvailableThenProductIsNotBought() {
        try {
            Product product = new Product();
            product.setName(productName);
            product.setQuantity(0);

            ProductRequest productRequest = new ProductRequest();
            productRequest.setName(productName);

            VendingMachine vendingMachine = new VendingMachine();
            vendingMachine.setInsertedMoney(randomAmount);

            when(vendingMachineRepository.findById(any())).thenReturn(Optional.of(vendingMachine));
            when(productRepository.findByName(any())).thenReturn(product);

            productService.buyProduct(productRequest);
        } catch (NotFoundRestException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    @Test
    void GivenProductIsAvailableWhenInsertedMoneyIsNotEnoughThenProductIsNotBought() {
        try {
            Product product = new Product();
            product.setName(productName);
            product.setQuantity(1);
            product.setPrice(randomAmount);

            ProductRequest productRequest = new ProductRequest();
            productRequest.setName(productName);


            VendingMachine vendingMachine = new VendingMachine();
            vendingMachine.setInsertedMoney(defaultAmount);

            when(vendingMachineRepository.findById(any())).thenReturn(Optional.of(vendingMachine));
            when(productRepository.findByName(any())).thenReturn(product);

            productService.buyProduct(productRequest);
        } catch (BadRequestRestException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    void WhenProductDtosAreCorrectThenProductIsAdded() {
        List<ProductDto> productDtos = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setName(productName);
            productDto.setQuantity(1);
            productDtos.add(productDto);
        }
        Product product = new Product();
        product.setQuantity(1);

        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(defaultAmount);

        when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);
        when(productRepository.findByName(any())).thenReturn(product);
        when(randomUtil.isFake()).thenReturn(false);

        productService.addProducts(productDtos);
        assertEquals(3, product.getQuantity());
    }

    @Test
    void GivenNameWhenProductIsFakeThenProductIsNotAdded() {
        List<ProductDto> productDtos = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setName(productName);
            productDto.setQuantity(1);
            productDtos.add(productDto);
        }
        Product product = new Product();
        product.setQuantity(1);

        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(defaultAmount);

        when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);
        when(productRepository.findByName(any())).thenReturn(product);
        when(randomUtil.isFake()).thenReturn(true);

        productService.addProducts(productDtos);
        assertEquals(1, product.getQuantity());
    }

    @Test
    void GivenRealProductWhenNameWrongThenProductIsNotAdded() {
        List<ProductDto> productDtos = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ProductDto productDto = new ProductDto();
            if (i == 0) {
                productDto.setName(productName);
            } else {
                productDto.setName(randomName);
            }
            productDto.setQuantity(1);
            productDtos.add(productDto);
        }
        Product product = new Product();
        product.setQuantity(1);

        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(defaultAmount);

        when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);
        when(productRepository.findByName(any())).thenReturn(product);
        when(randomUtil.isFake()).thenReturn(false);

        productService.addProducts(productDtos);
        assertEquals(2, product.getQuantity());
    }

    @Test
    void GivenNameCorrectWhenPriceWrongThenPriceNotUpdated() {
        try {
            UpdatePriceRequest updatePriceRequest = new UpdatePriceRequest();
            updatePriceRequest.setName(productName);
            updatePriceRequest.setPrice(badPrice);

            productService.updatePrice(updatePriceRequest);
        } catch (BadRequestRestException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    void GivenPriceCorrectWhenNameWrongThenPriceNotUpdated() {
        try {
            UpdatePriceRequest updatePriceRequest = new UpdatePriceRequest();
            updatePriceRequest.setName(randomName);
            updatePriceRequest.setPrice(randomAmount);

            productService.updatePrice(updatePriceRequest);
        } catch (BadRequestRestException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    void WhenNameAndPriceCorrectThenPriceIsUpdated() {
        UpdatePriceRequest updatePriceRequest = new UpdatePriceRequest();
        updatePriceRequest.setName(productName);
        updatePriceRequest.setPrice(randomAmount);

        Product product = new Product();

        when(productRepository.findByName(any())).thenReturn(product);

        productService.updatePrice(updatePriceRequest);

        assertEquals(randomAmount, product.getPrice());
    }

}