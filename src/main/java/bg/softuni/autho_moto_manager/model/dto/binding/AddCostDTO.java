package bg.softuni.autho_moto_manager.model.dto.binding;

import bg.softuni.autho_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class AddCostDTO {

    @NotNull
    private CostTypeEnum type;

    private String description;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotNull
    private CurrencyEntity currency;

    private BigDecimal fixedTransactionRate;

    private boolean isCompleted;

    private String vehicle;

    public AddCostDTO() {
    }

    public CostTypeEnum getType() {
        return type;
    }

    public AddCostDTO setType(CostTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddCostDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AddCostDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public AddCostDTO setCurrency(CurrencyEntity currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getFixedTransactionRate() {
        return fixedTransactionRate;
    }

    public AddCostDTO setFixedTransactionRate(BigDecimal fixedTransactionRate) {
        this.fixedTransactionRate = fixedTransactionRate;
        return this;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public AddCostDTO setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }

    public String getVehicle() {
        return vehicle;
    }

    public AddCostDTO setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }
}
