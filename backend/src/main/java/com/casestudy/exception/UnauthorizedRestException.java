package com.casestudy.exception;

import com.casestudy.exception.base.VendingMachineServiceRestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class UnauthorizedRestException extends VendingMachineServiceRestException {
    private static final Logger logger = LoggerFactory.getLogger(BadRequestRestException.class);

    public UnauthorizedRestException(String description) {
        super(description, HttpStatus.UNAUTHORIZED);
        logger.error("NotFoundRestException: " + description);
    }
}
