package com.casestudy.service;

import com.casestudy.TestConstants;
import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.exception.UnauthorizedRestException;
import com.casestudy.service.impl.AuthServiceImpl;
import com.casestudy.utility.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtUtil jwtUtil;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void WhenUuidIsCorrectThenJwtIsCreated() {
        when(jwtUtil.generateToken()).thenReturn("token");

        String token = authService.login(VendingMachineConstants.ADMIN_UUID);

        assertEquals("token", token);
    }

    @Test
    void WhenUuidIsNotCorrectThenJwtIsNotCreated() {
        try {
            authService.login(TestConstants.randomUuid);
        } catch (UnauthorizedRestException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        }
    }

    @Test
    void WhenUuidIsNullThenJwtIsNotCreated() {
        try {
            authService.login(null);
        } catch (UnauthorizedRestException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        }
    }
}