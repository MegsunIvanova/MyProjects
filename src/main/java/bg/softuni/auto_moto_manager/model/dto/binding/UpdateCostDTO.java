package bg.softuni.auto_moto_manager.model.dto.binding;

import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;

import java.math.BigDecimal;

public class UpdateCostDTO extends AddCostDTO {
    private Long id;

    public UpdateCostDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public UpdateCostDTO setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public UpdateCostDTO setType(CostTypeEnum type) {
        super.setType(type);
        return this;
    }

    @Override
    public UpdateCostDTO setDescription(String description) {
        super.setDescription(description);
        return this;
    }

    @Override
    public UpdateCostDTO setAmount(BigDecimal amount) {
        super.setAmount(amount);
        return this;
    }

    @Override
    public UpdateCostDTO setCurrency(String currency) {
        super.setCurrency(currency);
        return this;
    }

    @Override
    public UpdateCostDTO setTransactionExRate(BigDecimal transactionExRate) {
        super.setTransactionExRate(transactionExRate);
        return this;
    }

    @Override
    public UpdateCostDTO setCompleted(boolean completed) {
        super.setCompleted(completed);
        return this;
    }

    @Override
    public UpdateCostDTO setVehicle(String vehicle) {
        super.setVehicle(vehicle);
        return this;
    }
}
