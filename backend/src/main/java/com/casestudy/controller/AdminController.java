package com.casestudy.controller;

import com.casestudy.dto.AddProductsRequest;
import com.casestudy.dto.AdjustTemperatureRequest;
import com.casestudy.dto.LoginRequest;
import com.casestudy.dto.UpdatePriceRequest;
import com.casestudy.service.AuthService;
import com.casestudy.service.MachineService;
import com.casestudy.service.MoneyService;
import com.casestudy.service.ProductService;
import com.casestudy.utility.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MoneyService moneyService;
    @Autowired
    private ProductService productService;

    @Autowired
    private MachineService machineService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Login to admin panel")
    @ApiResponse(responseCode = "200", description = "Admin logins with UUID")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUuid());
        return ResponseEntity.ok().header(AUTHORIZATION, token).build();
    }

    @Operation(summary = "Check token validitiy")
    @ApiResponse(responseCode = "200", description = "Admin token gets checked")
    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestHeader(AUTHORIZATION) String token) {
        jwtUtil.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reset vending machine")
    @ApiResponse(responseCode = "200", description = "Vending machine is resetted.Zero products")
    @PostMapping("/reset")
    public ResponseEntity<?> reset(@RequestHeader(AUTHORIZATION) String token) {
        jwtUtil.validateToken(token);
        machineService.reset();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Collect money of vending machine")
    @ApiResponse(responseCode = "200", description = "Money of vending machine is collected by admin")
    @PostMapping("/collect-money")
    public ResponseEntity<?> collectMoney(@RequestHeader(AUTHORIZATION) String token) {
        jwtUtil.validateToken(token);
        moneyService.collectMoney();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add list of same products")
    @ApiResponse(responseCode = "200", description = "List of same products are added")
    @PostMapping("/add-products")
    public ResponseEntity<?> addProducts(@RequestHeader(AUTHORIZATION) String token, @RequestBody AddProductsRequest addProductsRequest) {
        jwtUtil.validateToken(token);
        return new ResponseEntity<>(productService.addProducts(addProductsRequest.getProductDtos()), OK);
    }

    @Operation(summary = "Update product price")
    @ApiResponse(responseCode = "200", description = "Admin updates product price")
    @PostMapping("/update-price")
    public ResponseEntity<?> updatePrice(@RequestHeader(AUTHORIZATION) String token, @RequestBody UpdatePriceRequest request) {
        jwtUtil.validateToken(token);
        productService.updatePrice(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update vending machine temperature")
    @ApiResponse(responseCode = "200", description = "Admin updates vending machine temperature")
    @PostMapping("/adjust-temperature")
    public ResponseEntity<?> adjustTemperature(@RequestHeader(AUTHORIZATION) String token, @RequestBody AdjustTemperatureRequest request) {
        jwtUtil.validateToken(token);
        machineService.adjustTemperature(request.getDegree());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get vending machine")
    @ApiResponse(responseCode = "200", description = "Admin gets vending machine")
    @GetMapping("/vending-machine")
    public ResponseEntity<?> getVendingMachine(@RequestHeader(AUTHORIZATION) String token) {
        jwtUtil.validateToken(token);
        return new ResponseEntity<>(machineService.getMachineForAdmin(), OK);
    }

}
