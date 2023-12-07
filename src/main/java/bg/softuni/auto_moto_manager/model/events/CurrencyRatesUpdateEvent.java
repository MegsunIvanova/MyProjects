package bg.softuni.auto_moto_manager.model.events;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class CurrencyRatesUpdateEvent extends ApplicationEvent {
    private final String currencyId;
    private final boolean success;
    private final LocalDateTime updatedOn;

    public CurrencyRatesUpdateEvent(Object source, String currencyId, boolean success, LocalDateTime updatedOn) {
        super(source);
        this.currencyId = currencyId;
        this.success = success;
        this.updatedOn = updatedOn;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public boolean isSuccess() {
        return success;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
}
