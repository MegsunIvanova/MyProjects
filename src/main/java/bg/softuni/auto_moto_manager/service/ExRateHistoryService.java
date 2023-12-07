package bg.softuni.auto_moto_manager.service;

import bg.softuni.auto_moto_manager.model.events.CurrencyRatesUpdateEvent;

public interface ExRateHistoryService {
    void saveExchangeRateHistory (CurrencyRatesUpdateEvent event);

    void deleteOldData();
}
