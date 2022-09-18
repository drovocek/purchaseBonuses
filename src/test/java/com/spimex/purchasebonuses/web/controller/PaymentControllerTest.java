package com.spimex.purchasebonuses.web.controller;

import com.spimex.purchasebonuses.dao.MockBankAccountRepository;
import com.spimex.purchasebonuses.dto.MoneyDto;
import com.spimex.purchasebonuses.web.dict.PaymentSource;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static com.spimex.purchasebonuses.dao.MockBankAccountRepository.START_CASH_COUNT;
import static com.spimex.purchasebonuses.dao.MockBankAccountRepository.START_NON_CASH_COUNT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl;

    public PaymentControllerTest(@LocalServerPort int port,
                                 @Value("${server.servlet.context-path}") String baseUrl) {
        this.baseUrl = "http://localhost:" + port + baseUrl;
    }

    static Stream<Arguments> parameterGenerator() {
        return Stream.of(
                //    1. Online/100
                Arguments.of(
                        PaymentSource.Online, // payment source
                        100L, // payment amount
                        new MoneyDto(// result money balance
                                START_CASH_COUNT, // cash balance
                                START_NON_CASH_COUNT - 100 // nonCash balance
                        ),
                        17L // 17% EMoney bonus
                ),

                //    2. Shop/120
                Arguments.of(PaymentSource.Shop, // payment source
                        120L, // payment amount
                        new MoneyDto( // result money balance
                                START_CASH_COUNT - 120, // cash balance
                                START_NON_CASH_COUNT // nonCash balance
                        ),
                        12L // 10% EMoney bonus
                ),

                //    3. Online/301
                Arguments.of(PaymentSource.Online, // payment source
                        301L, // payment amount
                        new MoneyDto( // result money balance
                                START_CASH_COUNT, // cash balance
                                START_NON_CASH_COUNT - 301L // nonCash balance
                        ),
                        51L // 17% EMoney bonus
                                + 90L // 30% EMoney bonus
                ),

                //    4. Online/17
                Arguments.of(PaymentSource.Online, // payment source
                        17L, // payment amount
                        new MoneyDto(// result money balance
                                START_CASH_COUNT, // cash balance
                                START_NON_CASH_COUNT - 17L // nonCash balance
                                        - 1 // minus 10% bank commission
                        ),
                        2L // 17% EMoney bonus
                ),

                //    5. Shop/1000
                Arguments.of(PaymentSource.Shop, // payment source
                        1000L, // payment amount
                        new MoneyDto(// result money balance
                                START_CASH_COUNT - 1000L, // cash balance
                                START_NON_CASH_COUNT // nonCash balance
                        ),
                        100L // 10% EMoney bonus
                                + 300L // 30% EMoney bonus
                ),

                //    6. Online/21
                Arguments.of(PaymentSource.Online, // payment source
                        21L, // payment amount
                        new MoneyDto(// result money balance
                                START_CASH_COUNT, // cash balance
                                START_NON_CASH_COUNT - 21L // nonCash balance
                        ),
                        3L // 17% EMoney bonus
                ),

                //    7. Shop/570
                Arguments.of(PaymentSource.Shop, // payment source
                        570L, // payment amount
                        new MoneyDto(// result money balance
                                START_CASH_COUNT - 570L, // cash balance
                                START_NON_CASH_COUNT // nonCash balance
                        ),
                        57L // 10% EMoney bonus
                                + 171L  // 30% EMoney bonus
                ),

                //    8. Online/700
                Arguments.of(PaymentSource.Online, // payment source
                        700L, // payment amount
                        new MoneyDto(// result money balance
                                START_CASH_COUNT, // cash
                                START_NON_CASH_COUNT - 700L // nonCash
                        ),
                        119L // 17% EMoney bonus
                                + 210L  // 30% EMoney bonus
                )
        );
    }

    @BeforeEach
    @DisplayName("Сброс баланса денег до исходного значения перед каждым тестом")
    void clearBalance() {
        MockBankAccountRepository.setStartBalance();
    }

    @Test
    @DisplayName("Изначальная сумма наличных/безналичный средств клиента = 5000 рублей.")
    void checkStartBalance() {
        assertThat(getBankAccountOfEMoney()).isEqualTo(0L);
        MoneyDto startMoney = getMoney();
        assertThat(startMoney.cashMoney()).isEqualTo(START_CASH_COUNT);
        assertThat(startMoney.nonCashMoney()).isEqualTo(START_NON_CASH_COUNT);
        assertThat(startMoney.nonCashMoney() + startMoney.cashMoney()).isEqualTo(5000L);
    }

    @MethodSource("parameterGenerator")
    @ParameterizedTest
    public void fullTest(PaymentSource paymentSource, long paymentAmount, MoneyDto expectedMoney, long expectedEMoney) {
        Condition<ResponseEntity<Object>> okStatus = new Condition<>(
                response -> response.getStatusCode().equals(HttpStatus.OK), "okStatus");

        assertThat(payment(paymentSource, paymentAmount)).has(okStatus);
        assertThat(getMoney()).isEqualTo(expectedMoney);
        assertThat(getBankAccountOfEMoney()).isEqualTo(expectedEMoney);
    }

    private long getBankAccountOfEMoney() {
        return this.restTemplate.getForObject(baseUrl + BankAccountOfEMoneyController.URL, Long.class);
    }

    private MoneyDto getMoney() {
        return this.restTemplate.getForObject(baseUrl + MoneyController.URL, MoneyDto.class);
    }

    private ResponseEntity<Object> payment(PaymentSource paymentSource, long amount) {
        return this.restTemplate.getForEntity(baseUrl + PaymentController.URL + "/%s/%s".formatted(paymentSource, amount), Object.class);
    }
}