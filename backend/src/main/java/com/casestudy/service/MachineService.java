package com.casestudy.service;

import com.casestudy.dto.VendingMachineDto;

public interface MachineService {
    void reset();

    void adjustTemperature(float degree);

    VendingMachineDto getMachineForCustomer();

    VendingMachineDto getMachineForAdmin();


}
