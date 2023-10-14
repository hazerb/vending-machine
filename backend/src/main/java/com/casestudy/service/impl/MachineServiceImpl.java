package com.casestudy.service.impl;

import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.dto.VendingMachineDto;
import com.casestudy.mapper.MachineMapper;
import com.casestudy.model.VendingMachine;
import com.casestudy.repository.ProductRepository;
import com.casestudy.repository.VendingMachineRepository;
import com.casestudy.service.MachineService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class MachineServiceImpl implements MachineService {
    @Autowired
    private VendingMachineRepository vendingMachineRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MachineMapper machineMapper;

    @Override
    public void reset() {
        VendingMachine vendingMachine = vendingMachineRepository.getReferenceById(VendingMachineConstants.VENDING_MACHINE_ID);
        vendingMachine.setInsertedMoney(VendingMachineConstants.DEFAULT_INSERTED_MONEY);
        vendingMachine.setTemperature(VendingMachineConstants.DEFAULT_TEMPERATURE);
        vendingMachine.setBalance(VendingMachineConstants.DEFAULT_BALANCE);
        productRepository.resetProducts();
    }

    @Override
    public void adjustTemperature(float degree) {
        VendingMachine vendingMachine = vendingMachineRepository.getReferenceById(VendingMachineConstants.VENDING_MACHINE_ID);
        vendingMachine.setTemperature(degree);
    }

    @Override
    public VendingMachineDto getMachineForCustomer() {
        VendingMachine vendingMachine = vendingMachineRepository.findById(VendingMachineConstants.VENDING_MACHINE_ID).orElseThrow(() -> new IllegalStateException("Expected vending machine not found"));
        return machineMapper.generateMachineDtoForCustomer(vendingMachine);
    }

    @Override
    public VendingMachineDto getMachineForAdmin() {
        VendingMachine vendingMachine = vendingMachineRepository.findById(VendingMachineConstants.VENDING_MACHINE_ID).orElseThrow(() -> new IllegalStateException("Expected vending machine not found"));
        return machineMapper.generateMachineDtoForAdmin(vendingMachine);
    }

}
