package com.casestudy.mapper;

import com.casestudy.dto.AddProductsResponse;
import com.casestudy.dto.GetProductsResponse;
import com.casestudy.dto.ProductDto;
import com.casestudy.dto.ProductResponse;
import com.casestudy.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public ProductResponse generateProductResponse(int change, int productQuantity) {
        return new ProductResponse(change, productQuantity);
    }

    public GetProductsResponse generateGetProductsResponse(List<Product> products) {
        GetProductsResponse getProductsResponse = new GetProductsResponse();
        List<ProductDto> productDtos = products.stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setQuantity(product.getQuantity());
            return productDto;
        }).toList();
        getProductsResponse.setProductDtos(productDtos);
        return getProductsResponse;
    }

    public AddProductsResponse generateAddProductsResponse(int realProductCount, int fakeProductCount) {
        return new AddProductsResponse(realProductCount, fakeProductCount);
    }
}
