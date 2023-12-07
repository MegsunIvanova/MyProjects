package bg.softuni.auto_moto_manager.model.dto.binding;

import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Map;

public class AddCostDTO {

    @NotNull (message = "Select a type!")
    private CostTypeEnum type;

    @Size (max = 200, message = "Description max length is 200 characters!")
    private String description;

    @Positive (message = "Amount must be positive!")
    @NotNull(message = "Fill out an amount!")
    private BigDecimal amount;

    @NotEmpty(message = "Select a currency!")
    private String currency;

    @Positive(message = "Rate must be positive number!")
    private BigDecimal transactionExRate;

    private boolean completed;

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

    public String getCurrency() {
        return currency;
    }

    public AddCostDTO setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getTransactionExRate() {
        return transactionExRate;
    }

    public AddCostDTO setTransactionExRate(BigDecimal transactionExRate) {
        this.transactionExRate = transactionExRate;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public AddCostDTO setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public String getVehicle() {
        return vehicle;
    }

    public AddCostDTO setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return amount.multiply(transactionExRate);
    }

    public BigDecimal getFixRate(Map<String, BigDecimal> currencies) {
        return currencies != null && currencies.containsKey(this.currency)
                ? currencies.get(this.currency)
                : BigDecimal.ZERO;
    }}
