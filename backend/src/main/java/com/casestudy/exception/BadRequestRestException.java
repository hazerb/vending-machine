package com.casestudy.exception;

import com.casestudy.exception.base.VendingMachineServiceRestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class BadRequestRestException extends VendingMachineServiceRestException {
    private static final Logger logger = LoggerFactory.getLogger(BadRequestRestException.class);

    public BadRequestRestException(String description) {
        super(description, HttpStatus.BAD_REQUEST);
        logger.error("BadRequestRestException: " + description);
    }

}
