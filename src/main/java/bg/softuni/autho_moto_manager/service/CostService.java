package bg.softuni.autho_moto_manager.service;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddCostViewDTO;

public interface CostService {
    AddCostViewDTO getAddCostViewDTO();

    void addCost(AddCostDTO addCostDTO);
}
