package com.casestudy.service;

import com.casestudy.dto.*;

import java.util.List;

public interface ProductService {
    ProductResponse buyProduct(ProductRequest productRequest);

    AddProductsResponse addProducts(List<ProductDto> productDtos);

    void updatePrice(UpdatePriceRequest request);

    GetProductsResponse getProducts();
}
