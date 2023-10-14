package com.casestudy.controller;

import com.casestudy.dto.MoneyDto;
import com.casestudy.dto.ProductRequest;
import com.casestudy.service.MachineService;
import com.casestudy.service.MoneyService;
import com.casestudy.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/")
@CrossOrigin
public class CustomerController {
    @Autowired
    private MoneyService moneyService;
    @Autowired
    private ProductService productService;

    @Autowired
    private MachineService machineService;

    @Operation(summary = "Insert money to vending machine")
    @ApiResponse(responseCode = "200", description = "User inserts money")
    @PostMapping("/insert-money")
    public ResponseEntity<?> insertMoney(@RequestBody MoneyDto moneyDto) {
        moneyService.insertMoney(moneyDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buy product")
    @ApiResponse(responseCode = "200", description = "Buy product")
    @PostMapping("/buy-product")
    public ResponseEntity<?> requestProduct(@RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.buyProduct(request), OK);
    }

    @Operation(summary = "Cancel request")
    @ApiResponse(responseCode = "200", description = "User can cancel request and get refund")
    @PostMapping("/cancel-request")
    public ResponseEntity<?> cancelRequest() {
        moneyService.cancelRequest();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get list of all products")
    @ApiResponse(responseCode = "200", description = "Can get list of products")
    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), OK);
    }

    @Operation(summary = "Get vending machine")
    @ApiResponse(responseCode = "200", description = "User gets vending machine")
    @GetMapping("/vending-machine")
    public ResponseEntity<?> getVendingMachine() {
        return new ResponseEntity<>(machineService.getMachineForCustomer(), OK);
    }

}
