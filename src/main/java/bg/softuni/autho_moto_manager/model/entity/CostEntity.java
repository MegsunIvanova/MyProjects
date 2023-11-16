package bg.softuni.autho_moto_manager.model.entity;

import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "costs")
public class CostEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CostTypeEnum type;

    private String description;

    @Column(nullable = false)
    private BigDecimal amount;
    @ManyToOne(optional = false)
    private CurrencyEntity currency;
    @Column(name = "transaction_rate", scale = 5)
    private BigDecimal transactionRate;
    private boolean completed;
    @ManyToOne
    private VehicleEntity vehicle;

    public CostEntity() {
    }

    public CostTypeEnum getType() {
        return type;
    }

    public CostEntity setType(CostTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CostEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CostEntity setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public CostEntity setCurrency(CurrencyEntity currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getTransactionRate() {
        return transactionRate;
    }

    public CostEntity setTransactionRate(BigDecimal fixedTransactionRate) {
        this.transactionRate = fixedTransactionRate;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public CostEntity setCompleted(boolean completed) {
        completed = completed;
        return this;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public CostEntity setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CostEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }
}
