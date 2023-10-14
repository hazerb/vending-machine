package com.casestudy.mapper;

import com.casestudy.dto.VendingMachineDto;
import com.casestudy.model.VendingMachine;
import org.springframework.stereotype.Component;

@Component
public class MachineMapper {
    public VendingMachineDto generateMachineDtoForCustomer(VendingMachine vendingMachine) {
        return createVendingMachine(vendingMachine);
    }

    public VendingMachineDto generateMachineDtoForAdmin(VendingMachine vendingMachine) {
        VendingMachineDto vendingMachineDto = createVendingMachine(vendingMachine);
        vendingMachineDto.setBalance(vendingMachine.getBalance());
        return vendingMachineDto;
    }

    private VendingMachineDto createVendingMachine(VendingMachine vendingMachine) {
        VendingMachineDto vendingmachineDto = new VendingMachineDto();
        vendingmachineDto.setTemperature(vendingMachine.getTemperature());
        vendingmachineDto.setInsertedMoney(vendingMachine.getInsertedMoney());
        return vendingmachineDto;
    }

}
