package com.casestudy.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VendingMachineServiceRestException extends RuntimeException {
    private String description;
    private HttpStatus status;
}
