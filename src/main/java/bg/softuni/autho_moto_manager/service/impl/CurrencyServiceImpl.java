package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.ExchangeRatesDTO;
import bg.softuni.autho_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.autho_moto_manager.repository.CurrencyRepository;
import bg.softuni.autho_moto_manager.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void updateRates(ExchangeRatesDTO exchangeRatesDTO) {
        LOGGER.info("Exchange Rates received {}", exchangeRatesDTO);

        List<CurrencyEntity> currencies = currencyRepository.findAll();

        currencies.forEach(c -> updateRate(c, exchangeRatesDTO));
    }

    private void updateRate(CurrencyEntity currency, ExchangeRatesDTO exchangeRatesDTO) {
        BigDecimal rateToBase = exchangeRatesDTO.rates().get(currency.getId());
        BigDecimal baseToBGN = exchangeRatesDTO.rates().get("BGN");

        if (baseToBGN == null) {
            LOGGER.error(currency.getId() + " exchange rate was NOT updated !!!");
            return;
        }

        if (currency.getId().equals(exchangeRatesDTO.base())) {
            currency.setRateToBGN(baseToBGN);
        } else if (rateToBase == null) {
            LOGGER.error(currency.getId() + " exchange rate was NOT updated !!!");
            return;
        } else {
            BigDecimal newRate = baseToBGN.divide(rateToBase, RoundingMode.UP);
            currency.setRateToBGN(newRate);
        }

        currencyRepository.save(currency);
        LOGGER.info(currency.getId() + " exchange rate was updated to " + currency.getRateToBGN());
    }
}
