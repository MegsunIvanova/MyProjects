package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class AddCostViewDTO {
    private CostTypeEnum[] costTypes;
    private List<String> currencies;


    public AddCostViewDTO() {
        this.costTypes = CostTypeEnum.values();
        this.currencies = new ArrayList<>();

    }

    public AddCostViewDTO(List<String> currencies) {
        this();
        this.currencies = currencies;
    }

    public CostTypeEnum[] getCostTypes() {
        return costTypes;
    }

    public List<String> getCurrencies() {
        return currencies;
    }
}
