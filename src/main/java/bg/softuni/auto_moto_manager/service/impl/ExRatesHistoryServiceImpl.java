package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.auto_moto_manager.model.entity.ExRateHistoryEntity;
import bg.softuni.auto_moto_manager.model.events.CurrencyRatesUpdateEvent;
import bg.softuni.auto_moto_manager.repository.CurrencyRepository;
import bg.softuni.auto_moto_manager.repository.ExRateHistoryRepository;
import bg.softuni.auto_moto_manager.service.ExRateHistoryService;
import bg.softuni.auto_moto_manager.service.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExRatesHistoryServiceImpl implements ExRateHistoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExRatesHistoryServiceImpl.class);
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

    @Scheduled(cron = "0 */1 * * * *")
//    @Scheduled(cron = "* * * */1 * *")
    @Override
    public void deleteOldData() {
        List<ExRateHistoryEntity> oldEntities = exRateHistoryRepository
                .findAllByUpdateOnBefore(LocalDateTime.now()
                        .minusDays(30));

        if (oldEntities.size() > 0) {
            exRateHistoryRepository.deleteAll(oldEntities);
        }

        LOGGER.info("Trigger clean up of exchange rate history. {} entities have been deleted.",
                oldEntities.size());
    }
}
