package com.casestudy.exception.handler;

import com.casestudy.exception.base.VendingMachineServiceRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(VendingMachineServiceRestException.class)
    public ResponseEntity<?> handleAtlasServiceRestException(VendingMachineServiceRestException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getDescription());
    }
}
