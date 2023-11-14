package bg.softuni.autho_moto_manager.service;

import bg.softuni.autho_moto_manager.model.dto.binding.CreateVehicleDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddVehicleViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleSummaryViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VehicleService {
    AddVehicleViewDTO getAddVehicleView ();

    void create(CreateVehicleDTO createVehicleDTO);

    Page<VehicleSummaryViewDTO> getAllVehicles(Pageable pageable);

    VehicleDetailsViewDTO getDetailsByUuid(String uuid);
}
