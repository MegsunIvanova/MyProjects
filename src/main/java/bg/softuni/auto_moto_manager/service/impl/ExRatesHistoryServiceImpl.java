package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.auto_moto_manager.model.entity.ExRateHistoryEntity;
import bg.softuni.auto_moto_manager.model.events.CurrencyRatesUpdateEvent;
import bg.softuni.auto_moto_manager.repository.CurrencyRepository;
import bg.softuni.auto_moto_manager.repository.ExRateHistoryRepository;
import bg.softuni.auto_moto_manager.service.ExRateHistoryService;
import bg.softuni.auto_moto_manager.service.exceptions.DatabaseException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ExRatesHistoryServiceImpl implements ExRateHistoryService {
    private final CurrencyRepository currencyRepository;
    private final ExRateHistoryRepository exRateHistoryRepository;

    public ExRatesHistoryServiceImpl(CurrencyRepository currencyRepository, ExRateHistoryRepository exRateHistoryRepository) {
        this.currencyRepository = currencyRepository;
        this.exRateHistoryRepository = exRateHistoryRepository;
    }

    @Override
    @EventListener(CurrencyRatesUpdateEvent.class)
    public void saveExchangeRateHistory(CurrencyRatesUpdateEvent event) {
        CurrencyEntity currencyEntity = currencyRepository
                .findById(event.getCurrencyId())
                .orElseThrow(() -> new DatabaseException(
                        "Currency with id " + event.getCurrencyId() + " was not found in DB!"));

        ExRateHistoryEntity exRateHistoryEntity = new ExRateHistoryEntity()
                .setCurrency(currencyEntity)
                .setUpdateOn(event.getUpdatedOn())
                .setSuccess(event.isSuccess());

        exRateHistoryRepository.save(exRateHistoryEntity);
    }
}
