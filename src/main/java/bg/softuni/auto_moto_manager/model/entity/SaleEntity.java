package bg.softuni.auto_moto_manager.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sales")
public class SaleEntity extends BaseEntity {
    @OneToOne
    private VehicleEntity vehicle;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    private CurrencyEntity currency;

    @Column(name = "transaction_rate", precision = 11, scale = 5, nullable = false)
    private BigDecimal transactionExRate;

    private String notes;

    public SaleEntity() {
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public SaleEntity setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SaleEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public SaleEntity setCurrency(CurrencyEntity currency) {
        this.currency = currency;
        if(transactionExRate == null) {
            setTransactionExRate(currency.getRateToBGN());
        }
        return this;
    }

    public BigDecimal getTransactionExRate() {
        return transactionExRate;
    }

    public SaleEntity setTransactionExRate(BigDecimal transactionExRate) {
        if (transactionExRate != null) {
            this.transactionExRate = transactionExRate;
        }
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public SaleEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

}
