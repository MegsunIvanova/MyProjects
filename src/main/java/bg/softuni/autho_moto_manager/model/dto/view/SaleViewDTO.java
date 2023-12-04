package bg.softuni.autho_moto_manager.model.dto.view;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SaleViewDTO {
    private BigDecimal price;
    private String currencyId;
    private BigDecimal transactionExRate;
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

    public BigDecimal getTransactionExRate() {
        return transactionExRate;
    }

    public SaleViewDTO setTransactionExRate(BigDecimal transactionExRate) {
        this.transactionExRate = transactionExRate;
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
        return this.price.multiply(this.transactionExRate)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
