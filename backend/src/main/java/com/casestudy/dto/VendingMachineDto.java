package com.casestudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendingMachineDto {
    private float temperature;
    private int insertedMoney;
    private int balance;
}
