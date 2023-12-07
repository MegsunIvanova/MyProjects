package bg.softuni.auto_moto_manager.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rate_update_history" )
public class ExRateHistoryEntity extends BaseEntity{
    @ManyToOne(optional = false)
    private CurrencyEntity currency;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false)
    private LocalDateTime updateOn;

    public ExRateHistoryEntity() {
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public ExRateHistoryEntity setCurrency(CurrencyEntity currency) {
        this.currency = currency;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ExRateHistoryEntity setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public LocalDateTime getUpdateOn() {
        return updateOn;
    }

    public ExRateHistoryEntity setUpdateOn(LocalDateTime updateOn) {
        this.updateOn = updateOn;
        return this;
    }
}
