package com.casestudy.service.impl;

import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.exception.UnauthorizedRestException;
import com.casestudy.service.AuthService;
import com.casestudy.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(String uuid) {
        if (uuid == null || !uuid.equals(VendingMachineConstants.ADMIN_UUID)) {
            throw new UnauthorizedRestException("User is not admin");
        }
        return jwtUtil.generateToken();
    }
}
