package com.casestudy.service;

import com.casestudy.dto.MoneyDto;

public interface MoneyService {
    void insertMoney(MoneyDto moneyDto);

    void cancelRequest();

    void collectMoney();

}
