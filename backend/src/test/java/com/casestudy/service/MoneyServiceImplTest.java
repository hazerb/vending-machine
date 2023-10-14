package com.casestudy.service;

import com.casestudy.dto.MoneyDto;
import com.casestudy.exception.BadRequestRestException;
import com.casestudy.model.VendingMachine;
import com.casestudy.repository.VendingMachineRepository;
import com.casestudy.service.impl.MoneyServiceImpl;
import com.casestudy.utility.RandomUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.casestudy.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class MoneyServiceImplTest {

    @InjectMocks
    private MoneyServiceImpl moneyService;

    @Mock
    private VendingMachineRepository vendingMachineRepository;

    @MockBean
    private RandomUtil randomUtil;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void WhenMoneyFakeThenItIsNotInserted() {
        try {
            MoneyDto moneyDto = new MoneyDto();
            moneyDto.setAmount(correctAmount);

            VendingMachine vendingMachine = new VendingMachine();
            vendingMachine.setInsertedMoney(correctAmount);

            when(randomUtil.isFake()).thenReturn(true);
            when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);

            moneyService.insertMoney(moneyDto);
        } catch (BadRequestRestException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    void WhenMoneyAmountWrongThenItIsNotInserted() {
        try {
            MoneyDto moneyDto = new MoneyDto();
            moneyDto.setAmount(defaultAmount);

            VendingMachine vendingMachine = new VendingMachine();
            vendingMachine.setInsertedMoney(randomAmount);

            when(randomUtil.isFake()).thenReturn(false);
            when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);

            moneyService.insertMoney(moneyDto);
        } catch (BadRequestRestException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }

    @Test
    void WhenMoneyDtoCorrectThenItIsInserted() {
        MoneyDto moneyDto = new MoneyDto();
        moneyDto.setAmount(correctAmount);

        VendingMachine vendingMachine = new VendingMachine();

        when(randomUtil.isFake()).thenReturn(false);
        when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);

        moneyService.insertMoney(moneyDto);

        assertEquals(correctAmount, vendingMachine.getInsertedMoney());
    }

    @Test
    void cancelsRequest() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(randomAmount);

        when(vendingMachineRepository.findById(any())).thenReturn(Optional.of(vendingMachine));

        moneyService.cancelRequest();

        assertEquals(defaultAmount, vendingMachine.getInsertedMoney());
    }

    @Test
    void collectsMoney() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(randomAmount);
        vendingMachine.setBalance(randomAmount);

        when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);

        moneyService.collectMoney();

        assertEquals(defaultAmount, vendingMachine.getBalance());
    }

}