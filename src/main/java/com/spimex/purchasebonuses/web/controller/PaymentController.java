package com.spimex.purchasebonuses.web.controller;

import com.spimex.purchasebonuses.exception.NotSupportedDataValueException;
import com.spimex.purchasebonuses.exception.NotSupportedTypeException;
import com.spimex.purchasebonuses.service.payment.PaymentService;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import com.spimex.purchasebonuses.web.dto.JsonException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.spimex.purchasebonuses.exception.NotSupportedDataValueException.NOT_SUPPORTED_DATA_TEMPLATE;
import static com.spimex.purchasebonuses.exception.NotSupportedTypeException.NOT_SUPPORTED_TYPE_TEMPLATE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = PaymentController.URL)
public class PaymentController {

    public static final String URL = "/payment";

    private final PaymentService service;

    @Operation(summary = "Make Shop/Online payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success payment")})
    @GetMapping(value = "/{paymentSource}/{amount}")
    private ResponseEntity<Object> payment(@PathVariable PaymentSource paymentSource, @PathVariable long amount) {
        log.info("payment(paymentSource: {}, amount: {})", paymentSource, amount);
        throwIfNotValid(paymentSource, amount);
        this.service.makePayment(paymentSource, amount);
        return ResponseEntity.ok().build();
    }

    private void throwIfNotValid(PaymentSource paymentSource, long amount) {
        boolean supportedTypes = paymentSource.equals(PaymentSource.Online) || paymentSource.equals(PaymentSource.Shop);
        if (!supportedTypes) {
            throw new NotSupportedTypeException(NOT_SUPPORTED_TYPE_TEMPLATE.formatted(paymentSource));
        }

        if (amount <= 0) {
            throw new NotSupportedDataValueException(NOT_SUPPORTED_DATA_TEMPLATE.formatted(amount));
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonException> handleExceptions(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new JsonException(ex.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }
}