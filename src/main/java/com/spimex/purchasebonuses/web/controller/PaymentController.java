package com.spimex.purchasebonuses.web.controller;

import com.spimex.purchasebonuses.service.PaymentService;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import com.spimex.purchasebonuses.web.dto.JsonException;
import com.spimex.purchasebonuses.web.exception.NotSupportedTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    private final PaymentService service;

    @GetMapping(value = "/{paymentSource}/{amount}")
    private ResponseEntity<Object> payment(@PathVariable PaymentSource paymentSource, @PathVariable long amount) {
        log.info("payment(paymentSource: {}, amount: {})", paymentSource, amount);
        if (paymentSource.equals(PaymentSource.Online) || paymentSource.equals(PaymentSource.Shop)) {
            this.service.makePayment(paymentSource, amount);
            return ResponseEntity.ok().build();
        }
        throw new NotSupportedTypeException("Payment source '%s' does not supported".formatted(paymentSource));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonException> handleExceptions(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new JsonException(ex.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
}