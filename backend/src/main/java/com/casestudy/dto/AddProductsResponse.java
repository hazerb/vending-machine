package com.casestudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductsResponse {
    private int realProductCount;
    private int fakeProductCount;
}
