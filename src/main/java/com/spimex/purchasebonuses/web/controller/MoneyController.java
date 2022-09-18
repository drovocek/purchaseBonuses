package com.spimex.purchasebonuses.web.controller;

import com.spimex.purchasebonuses.service.BankAccountService;
import com.spimex.purchasebonuses.web.dto.MoneyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/money")
public class MoneyController {

    private final BankAccountService service;

    @GetMapping
    private MoneyDto getMoney() {
        return this.service.getMoneyCount();
    }
}
