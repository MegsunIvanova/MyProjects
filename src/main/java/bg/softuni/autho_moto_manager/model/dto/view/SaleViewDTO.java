package bg.softuni.autho_moto_manager.model.dto.view;

import java.math.BigDecimal;

public class SaleViewDTO {
    private BigDecimal price;
    private String currencyId;
    private BigDecimal transactionRate;
    private String notes;

    public SaleViewDTO() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SaleViewDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public SaleViewDTO setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public BigDecimal getTransactionRate() {
        return transactionRate;
    }

    public SaleViewDTO setTransactionRate(BigDecimal transactionRate) {
        this.transactionRate = transactionRate;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public SaleViewDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public BigDecimal getSalePriceInBGN() {
        return this.price.multiply(this.transactionRate);
    }
}
