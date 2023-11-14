package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.entity.CostEntity;

import java.math.BigDecimal;

public class CostViewDTO {

    private Long id;
    private String costType;
    private String description;
    private BigDecimal amount;
    private String currencyId;
    private BigDecimal currencyRateToBGN;

    public CostViewDTO(CostEntity costEntity) {
        this.id = costEntity.getId();
        this.costType = costEntity.getType().name();
        this.description = costEntity.getDescription();
        this.amount = costEntity.getAmount();
        this.currencyId = costEntity.getCurrency().getId();
        this.currencyRateToBGN = costEntity.getCurrency().getRateToBGN();
    }

    public Long getId() {
        return id;
    }

    public String getCostType() {
        return costType;
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
}
