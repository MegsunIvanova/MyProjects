package bg.softuni.autho_moto_manager.model.entity;

import bg.softuni.autho_moto_manager.model.dto.view.VehicleSummaryViewDTO;
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
    private BigDecimal transactionRate;

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
        if(transactionRate == null) {
            setTransactionRate(currency.getRateToBGN());
        }
        return this;
    }

    public BigDecimal getTransactionRate() {
        return transactionRate;
    }

    public SaleEntity setTransactionRate(BigDecimal transactionRate) {
        if (transactionRate != null) {
            this.transactionRate = transactionRate;
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

    public BigDecimal getSalePriceInBGN() {
        return this.price
                .multiply(this.transactionRate);
    }
}
