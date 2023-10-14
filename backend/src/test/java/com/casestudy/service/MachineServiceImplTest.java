package com.casestudy.service;

import com.casestudy.dto.VendingMachineDto;
import com.casestudy.mapper.MachineMapper;
import com.casestudy.model.VendingMachine;
import com.casestudy.repository.ProductRepository;
import com.casestudy.repository.VendingMachineRepository;
import com.casestudy.service.impl.MachineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.casestudy.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class MachineServiceImplTest {

    @InjectMocks
    private MachineServiceImpl machineService;

    @Mock
    private VendingMachineRepository vendingMachineRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MachineMapper machineMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ResetsVendingMachine() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(randomAmount);

        when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);
        doNothing().when(productRepository).resetProducts();

        machineService.reset();

        assertEquals(defaultAmount, vendingMachine.getInsertedMoney());
    }

    @Test
    void adjustsTemperature() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(randomAmount);

        when(vendingMachineRepository.getReferenceById(any())).thenReturn(vendingMachine);

        machineService.adjustTemperature(randomTemperature);

        assertEquals(randomTemperature, vendingMachine.getTemperature());
    }

    @Test
    void getsMachineForCustomer() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setInsertedMoney(randomAmount);

        VendingMachineDto vendingMachineDto = new VendingMachineDto();
        vendingMachine.setInsertedMoney(randomAmount);

        when(vendingMachineRepository.findById(any())).thenReturn(Optional.of(vendingMachine));
        when(machineMapper.generateMachineDtoForCustomer(vendingMachine)).thenReturn(vendingMachineDto);

        VendingMachineDto response = machineService.getMachineForCustomer();

        assertEquals(vendingMachineDto.getTemperature(), response.getTemperature());
    }

    @Test
    void getsMachineForAdmin() {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.setBalance(randomAmount);

        VendingMachineDto vendingMachineDto = new VendingMachineDto();
        vendingMachine.setBalance(randomAmount);

        when(vendingMachineRepository.findById(any())).thenReturn(Optional.of(vendingMachine));
        when(machineMapper.generateMachineDtoForAdmin(vendingMachine)).thenReturn(vendingMachineDto);

        VendingMachineDto response = machineService.getMachineForAdmin();

        assertEquals(vendingMachineDto.getBalance(), response.getBalance());
    }

}