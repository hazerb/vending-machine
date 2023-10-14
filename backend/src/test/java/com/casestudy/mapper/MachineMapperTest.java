package com.casestudy.mapper;

import com.casestudy.dto.VendingMachineDto;
import com.casestudy.model.VendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static com.casestudy.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MachineMapperTest {

    @InjectMocks
    private MachineMapper machineMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void machineDtoForCustomerIsGenerated() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setBalance(defaultAmount);
        vendingMachine.setInsertedMoney(randomAmount);
        vendingMachine.setTemperature(randomTemperature);

        VendingMachineDto vendingMachineDto = machineMapper.generateMachineDtoForCustomer(vendingMachine);
        assertEquals(randomAmount, vendingMachineDto.getInsertedMoney());
        assertEquals(randomTemperature, vendingMachineDto.getTemperature());
    }

    @Test
    void machineDtoForAdminIsGenerated() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setBalance(randomAmount);
        vendingMachine.setInsertedMoney(randomAmount);
        vendingMachine.setTemperature(randomTemperature);
        vendingMachine.setBalance(randomAmount);

        VendingMachineDto vendingMachineDto = machineMapper.generateMachineDtoForAdmin(vendingMachine);
        assertEquals(randomAmount, vendingMachineDto.getInsertedMoney());
        assertEquals(randomTemperature, vendingMachineDto.getTemperature());
        assertEquals(randomAmount, vendingMachineDto.getBalance());
    }

}