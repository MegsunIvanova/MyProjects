package bg.softuni.auto_moto_manager.model.dto.binding;

import bg.softuni.auto_moto_manager.model.validation.AllCostsCompleted;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class SaleDTO {
    @NotEmpty(message = "Vehicle uuid can not be empty!")
    @AllCostsCompleted
    private String vehicle;//vehicleUuid

    @Positive(message = "Price must be positive number")
    @NotNull(message = "Price can not be null.")
    private BigDecimal price;

    @NotEmpty(message = "Currency must be selected!")
    private String currency; //currencyId

    @Positive(message = "Rate must be positive number")
    private BigDecimal transactionExRate;

    @Size(max = 60)
    private String notes;

    public SaleDTO() {
    }

    public String getVehicle() {
        return vehicle;
    }

    public SaleDTO setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SaleDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public SaleDTO setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getTransactionExRate() {
        return transactionExRate;
    }

    public SaleDTO setTransactionExRate(BigDecimal transactionExRate) {
        this.transactionExRate = transactionExRate;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public SaleDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
