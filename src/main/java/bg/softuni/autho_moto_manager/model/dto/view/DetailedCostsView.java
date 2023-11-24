package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailedCostsView {

    private CostTypeEnum[] costTypes;
    private Map<CostTypeEnum, BigDecimal> completedCostsAmount;
    private Map<CostTypeEnum, BigDecimal> uncompletedCostsAmount;
    private Map<CostTypeEnum, List<CostViewDTO>> costsByType;

    private boolean sold;

    public DetailedCostsView(Map<CostTypeEnum, BigDecimal> completedCostsAmount,
                             Map<CostTypeEnum, BigDecimal> uncompletedCostsAmount,
                             Map<CostTypeEnum, List<CostViewDTO>> costsByType,
                             boolean sold) {
        this.costTypes = CostTypeEnum.values();
        this.completedCostsAmount = completedCostsAmount;
        this.uncompletedCostsAmount = uncompletedCostsAmount;
        this.costsByType = costsByType;
        this.sold = sold;
    }

    public CostTypeEnum[] getCostTypes() {
        return costTypes;
    }

    public Map<CostTypeEnum, BigDecimal> getCompletedCostsAmount() {
        return completedCostsAmount;
    }

    public Map<CostTypeEnum, BigDecimal> getUncompletedCostsAmount() {
        return uncompletedCostsAmount;
    }

    public Map<CostTypeEnum, List<CostViewDTO>> getCostsByType() {
        return costsByType;
    }

    public BigDecimal getCostSumInBGN(CostTypeEnum costType) {
        return completedCostsAmount.getOrDefault(costType, BigDecimal.ZERO)
                .add(uncompletedCostsAmount.getOrDefault(costType, BigDecimal.ZERO));
    }

    public BigDecimal getCostSumInBGN(CostTypeEnum costType, boolean completed) {
        return completed
                ? completedCostsAmount.getOrDefault(costType, BigDecimal.ZERO)
                : uncompletedCostsAmount.getOrDefault(costType, BigDecimal.ZERO);
    }

    public List<CostViewDTO> getCostsByType(CostTypeEnum costType) {
        return costsByType.getOrDefault(costType, new ArrayList<>());
    }

    public BigDecimal getCompletedCostsSum() {
        return completedCostsAmount.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getUncompletedCostsSum() {
        return uncompletedCostsAmount.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCostsSum() {
        return getCompletedCostsSum().add(getUncompletedCostsSum());
    }

    public boolean isSold() {
        return sold;
    }
}


