package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.ExchangeRatesDTO;
import bg.softuni.autho_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.autho_moto_manager.repository.CurrencyRepository;
import bg.softuni.autho_moto_manager.service.CurrencyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
class CurrencyServiceImplTestIT {

    @Autowired
    private CurrencyService currencyServiceToTest;

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        currencyRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        currencyRepository.deleteAll();
    }

    @ParameterizedTest(name = "Conversion USD/BGN exRate {0}, expected {1}")
    @MethodSource("testDataUSD")
    void test_USD_TO_BGN(Double exchangeRate, Double expectedValue) {
        ExchangeRatesDTO testExRate =
                new ExchangeRatesDTO("USD", Map.of("BGN", BigDecimal.valueOf(exchangeRate)));

        currencyServiceToTest.updateRates(testExRate);

        Optional<CurrencyEntity> exchangeRate_USD_BGN = currencyRepository.findById("USD");

        Assertions.assertTrue(exchangeRate_USD_BGN.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(expectedValue).setScale(5, RoundingMode.HALF_UP),
                exchangeRate_USD_BGN.get().getRateToBGN());
    }

    @ParameterizedTest(name = "Conversion EUR/BGN exRateBGN {0}, exRateEUR {1}, expected {2}")
    @MethodSource("testDataEUR")
    void test_EUR_TO_BGN(Double exchangeRateBGN, Double exchangeRateEUR, Double expectedValue) {
        ExchangeRatesDTO testExRate =
                new ExchangeRatesDTO("USD",
                        Map.of("BGN", BigDecimal.valueOf(exchangeRateBGN),
                                "EUR", BigDecimal.valueOf(exchangeRateEUR)));

        currencyServiceToTest.updateRates(testExRate);

        Optional<CurrencyEntity> exchangeRate_EUR_BGN = currencyRepository.findById("EUR");

        Assertions.assertTrue(exchangeRate_EUR_BGN.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(expectedValue).setScale(5, RoundingMode.UP),
                exchangeRate_EUR_BGN.get().getRateToBGN());
    }

    private static Stream<Arguments> testDataUSD() {
        return Stream.of(
                Arguments.of(1.78046, 1.78046),
                Arguments.of(1.99, 1.99000),
                Arguments.of(1.6, 1.60000),
                Arguments.of(1.755688, 1.75569),
                Arguments.of(1.755685, 1.75568),
                Arguments.of(1.755684, 1.75568)
        );
    }

    private static Stream<Arguments> testDataEUR() {
        return Stream.of(
                Arguments.of(1.78046, 0.91033, 1.95584),
                Arguments.of(1.854, 0.94899, 1.95366),
                Arguments.of(1.84515, 0.93767, 1.96780),
                Arguments.of(1.777515, 0.5444, 3.26509),
                Arguments.of(1.0, 1.0, 1.00000)
        );
    }

}