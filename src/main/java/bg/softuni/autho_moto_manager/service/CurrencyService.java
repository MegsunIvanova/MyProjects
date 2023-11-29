package bg.softuni.autho_moto_manager.service;

import bg.softuni.autho_moto_manager.model.dto.binding.ExchangeRatesDTO;

public interface CurrencyService {
    void updateRates(ExchangeRatesDTO exchangeRatesDTO);

}
