package com.casestudy.service.impl;

import com.casestudy.constant.VendingMachineConstants;
import com.casestudy.dto.MoneyDto;
import com.casestudy.exception.BadRequestRestException;
import com.casestudy.model.VendingMachine;
import com.casestudy.repository.VendingMachineRepository;
import com.casestudy.service.MoneyService;
import com.casestudy.utility.RandomUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    private RandomUtil randomUtil;

    @Autowired
    private VendingMachineRepository vendingMachineRepository;

    @Override
    public void insertMoney(MoneyDto moneyDto) {
        checkFake(randomUtil.isFake());
        checkAmount(moneyDto.getAmount());
        VendingMachine vendingMachine = vendingMachineRepository.getReferenceById(VendingMachineConstants.VENDING_MACHINE_ID);
        vendingMachine.setInsertedMoney(vendingMachine.getInsertedMoney() + moneyDto.getAmount());
    }

    private void checkFake(boolean isFake) {
        if (isFake) {
            throw new BadRequestRestException("Money is fake");
        }
    }

    private void checkAmount(int amount) {
        if (amount != 1 && amount != 5 && amount != 10 && amount != 20) {
            throw new BadRequestRestException("Amount is wrong");
        }
    }

    @Override
    public void cancelRequest() {
        VendingMachine vendingMachine = vendingMachineRepository.findById(VendingMachineConstants.VENDING_MACHINE_ID).orElseThrow(() -> new IllegalStateException("Expected vending machine not found"));
        vendingMachine.setInsertedMoney(VendingMachineConstants.DEFAULT_INSERTED_MONEY);
    }

    @Override
    public void collectMoney() {
        VendingMachine vendingMachine = vendingMachineRepository.getReferenceById(VendingMachineConstants.VENDING_MACHINE_ID);
        vendingMachine.setBalance(VendingMachineConstants.DEFAULT_BALANCE);
    }

}
