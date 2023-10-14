package com.casestudy.service.impl;

import com.casestudy.constant.ProductType;
import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.dto.*;
import com.casestudy.exception.BadRequestRestException;
import com.casestudy.exception.NotFoundRestException;
import com.casestudy.mapper.ProductMapper;
import com.casestudy.model.Product;
import com.casestudy.model.VendingMachine;
import com.casestudy.repository.ProductRepository;
import com.casestudy.repository.VendingMachineRepository;
import com.casestudy.service.ProductService;
import com.casestudy.utility.RandomUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VendingMachineRepository vendingMachineRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RandomUtil randomUtil;

    @Override
    public ProductResponse buyProduct(ProductRequest productRequest) {
        VendingMachine vendingMachine = vendingMachineRepository.findById(VendingMachineConstants.VENDING_MACHINE_ID).orElseThrow(() -> new IllegalStateException("Expected vending machine not found"));
        if (!checkName(productRequest.getName())) {
            throw new BadRequestRestException("Wrong Product Name");
        }
        Product product = productRepository.findByName(productRequest.getName());
        checkQuantity(product.getQuantity());
        int productPrice = product.getPrice();
        int change = vendingMachine.getInsertedMoney() - productPrice;
        if (change < 0) {
            throw new BadRequestRestException("Not enough money to buy product");
        }
        product.setQuantity(product.getQuantity() - 1);
        vendingMachine.setBalance(vendingMachine.getBalance() + productPrice);
        vendingMachine.setInsertedMoney(VendingMachineConstants.DEFAULT_INSERTED_MONEY);
        return productMapper.generateProductResponse(change, product.getQuantity());
    }

    private void checkQuantity(int quantity) {
        if (quantity == 0) {
            throw new NotFoundRestException("Product not found");
        }
    }

    @Override
    public AddProductsResponse addProducts(List<ProductDto> productDtos) {
        if (productDtos == null || productDtos.isEmpty()) {
            throw new BadRequestRestException("No products");
        }
        String currentProductName = null;
        String previousProductName = null;
        int addedProductCount = 0;
        for (ProductDto productDto : productDtos) {
            currentProductName = productDto.getName();
            if (!randomUtil.isFake() && checkName(currentProductName) && isSameProduct(previousProductName, currentProductName)) {
                addedProductCount++;
                previousProductName = currentProductName;
            }
        }
        Product product = productRepository.findByName(currentProductName);
        product.setQuantity(product.getQuantity() + addedProductCount);
        return productMapper.generateAddProductsResponse(addedProductCount, productDtos.size() - addedProductCount);
    }

    private Boolean isSameProduct(String previousProductName, String currentProductName) {
        return previousProductName == null || previousProductName.equals(currentProductName);
    }

    private void checkPrice(int price) {
        if (price < 0) {
            throw new BadRequestRestException("Wrong price");
        }
    }

    private boolean checkName(String productName) {
        String upperProductName = productName.toUpperCase();
        return upperProductName.equals(ProductType.COKE.name()) || upperProductName.equals(ProductType.SODA.name()) || upperProductName.equals(ProductType.WATER.name());
    }


    @Override
    public void updatePrice(UpdatePriceRequest request) {
        int price = request.getPrice();
        checkPrice(price);
        if (!checkName(request.getName())) {
            throw new BadRequestRestException("Wrong Product Name");
        }
        Product product = productRepository.findByName(request.getName());
        product.setPrice(price);
    }

    @Override
    public GetProductsResponse getProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.generateGetProductsResponse(products);
    }
}
