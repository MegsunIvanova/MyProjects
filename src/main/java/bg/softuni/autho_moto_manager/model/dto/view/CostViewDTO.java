package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;

import java.math.BigDecimal;

public class CostViewDTO {
    private Long id;
    private CostTypeEnum type;
    private String description;
    private BigDecimal amount;
    private String currencyId;
    private BigDecimal currencyRateToBGN;
    private BigDecimal amountInBGN;
    private boolean completed;

    public CostViewDTO(CostEntity costEntity) {
        this.id = costEntity.getId();
        this.type = costEntity.getType();
        this.description = costEntity.getDescription();
        this.amount = costEntity.getAmount();
        this.currencyId = costEntity.getCurrency().getId();
        this.currencyRateToBGN = costEntity.getTransactionRate();
        this.amountInBGN = costEntity.getAmountInBGN();
        this.completed = costEntity.isCompleted();
    }

    public Long getId() {
        return id;
    }

    public CostTypeEnum getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public BigDecimal getCurrencyRateToBGN() {
        return currencyRateToBGN;
    }

    public BigDecimal getAmountInBGN() {
        return amountInBGN;
    }

    public boolean isCompleted() {
        return completed;
    }
}
