package com.spimex.purchasebonuses.web.controller;

import com.spimex.purchasebonuses.dto.MoneyDto;
import com.spimex.purchasebonuses.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = MoneyController.URL)
public class MoneyController {

    public static final String URL = "/money";

    private final BankAccountService service;

    @GetMapping
    private MoneyDto getMoney() {
        return this.service.getMoneyCount();
    }
}
