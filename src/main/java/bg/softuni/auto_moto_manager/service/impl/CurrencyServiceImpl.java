package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.binding.ExchangeRatesDTO;
import bg.softuni.auto_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.auto_moto_manager.repository.CurrencyRepository;
import bg.softuni.auto_moto_manager.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

        List<String> currencyIds = new ArrayList<>();
        currencyIds.add(exchangeRatesDTO.base());
        currencyIds.addAll(exchangeRatesDTO.rates().keySet());

        currencyIds.forEach(c -> updateRates(c, exchangeRatesDTO));
    }

    private void updateRates(String currencyId, ExchangeRatesDTO exchangeRatesDTO) {
        BigDecimal baseToBGN = exchangeRatesDTO.rates().get("BGN");
        BigDecimal rateToBase = exchangeRatesDTO.rates().get(currencyId);
        CurrencyEntity currencyEntity = new CurrencyEntity().setId(currencyId);

        if (baseToBGN == null) {
            LOGGER.error(currencyId + " exchange rate was NOT updated !!!");
            return;
        }

        if (currencyId.equals(exchangeRatesDTO.base())) {
            currencyEntity.setRateToBGN(baseToBGN);
        } else if (rateToBase == null) {
            LOGGER.error(currencyId + " exchange rate was NOT updated !!!");
            return;
        } else {
            BigDecimal newRate = baseToBGN.divide(rateToBase, 5, RoundingMode.HALF_UP);
            currencyEntity.setRateToBGN(newRate);
        }

        currencyRepository.save(currencyEntity);
        LOGGER.info(currencyEntity.getId() + " exchange rate was updated to " + currencyEntity.getRateToBGN());
    }
}
