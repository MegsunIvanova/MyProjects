package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DetailedCostsView {

    private CostTypeEnum[] costTypes;
    private Map<CostTypeEnum, List<CostViewDTO>> completedCosts;
    private Map<CostTypeEnum, List<CostViewDTO>> planedCosts;

    public DetailedCostsView(Map<CostTypeEnum, List<CostViewDTO>> completedCosts,
                             Map<CostTypeEnum, List<CostViewDTO>> planedCosts) {
        this.costTypes = CostTypeEnum.values();
        this.completedCosts = completedCosts;
        this.planedCosts = planedCosts;
    }

    public Map<CostTypeEnum, List<CostViewDTO>> getCompletedCosts() {
        return completedCosts;
    }

    public Map<CostTypeEnum, List<CostViewDTO>> getPlanedCosts() {
        return planedCosts;
    }

    public BigDecimal getTotalInBGNByTypeAndStatus(CostTypeEnum costType, Boolean completed) {
        if (completed) {
            return this.completedCosts.containsKey(costType)
                    ? getCostsSumInBGN(this.completedCosts.get(costType))
                    : BigDecimal.ZERO;
        } else {
            return this.planedCosts.containsKey(costType)
                    ? getCostsSumInBGN(this.planedCosts.get(costType))
                    : BigDecimal.ZERO;
        }
    }

    public CostTypeEnum[] getCostTypes() {
        return costTypes;
    }

    public boolean contains(CostTypeEnum costType) {
        return completedCosts.containsKey(costType) || planedCosts.containsKey(costType);
    }

    public BigDecimal getTotalInBGNByType(CostTypeEnum costType) {
        return getTotalInBGNByTypeAndStatus(costType, true)
                .add(getTotalInBGNByTypeAndStatus(costType, false));
    }

    private BigDecimal getCostsSumInBGN(List<CostViewDTO> costs) {
        return costs != null
                ? costs.stream()
                .map(CostViewDTO::getAmountInBGN)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;
    }
}
