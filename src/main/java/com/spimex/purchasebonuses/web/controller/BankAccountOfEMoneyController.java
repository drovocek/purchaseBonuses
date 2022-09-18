package com.spimex.purchasebonuses.web.controller;

import com.spimex.purchasebonuses.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = BankAccountOfEMoneyController.URL)
public class BankAccountOfEMoneyController {

    public static final String URL = "/bankAccountOfEMoney";

    private final BankAccountService service;

    @Operation(summary = "Get all electronic money")
    @GetMapping
    private long getBankAccountOfEMoney() {
        return this.service.getEMoneyCount();
    }
}
