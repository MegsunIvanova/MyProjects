package bg.softuni.auto_moto_manager.service;

import bg.softuni.auto_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.auto_moto_manager.model.dto.binding.UpdateCostDTO;
import bg.softuni.auto_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.auto_moto_manager.model.dto.view.DetailedCostsView;

public interface CostService {
    AddCostViewDTO getAddCostViewDTO();

    void addCost(AddCostDTO addCostDTO);

    DetailedCostsView getDetailedCostsView(String vehicle);

    void delete(Long id);

    UpdateCostDTO getUpdateCostDTO(Long costId, String vehicleUuid);

    void updateCost(UpdateCostDTO updateCostDTO);
}
