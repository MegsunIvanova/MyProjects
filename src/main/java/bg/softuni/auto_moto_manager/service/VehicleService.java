package bg.softuni.auto_moto_manager.service;

import bg.softuni.auto_moto_manager.model.dto.binding.CreateVehicleDTO;
import bg.softuni.auto_moto_manager.model.dto.view.AddVehicleViewDTO;
import bg.softuni.auto_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.auto_moto_manager.model.dto.view.VehicleSummaryViewDTO;
import bg.softuni.auto_moto_manager.model.dto.view.VehiclesUuidDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface VehicleService {
    AddVehicleViewDTO getAddVehicleView ();

    void create(CreateVehicleDTO createVehicleDTO, UserDetails principal);

    Page<VehicleSummaryViewDTO> getAllVehicles(Pageable pageable);

    VehicleDetailsViewDTO getDetailsByUuid(String uuid);

    List<VehiclesUuidDTO> getMyVehiclesList(UserDetails principal);

}
