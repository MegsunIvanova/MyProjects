package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.view.SaleViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehiclesUuidDTO;
import bg.softuni.autho_moto_manager.service.SaleService;
import bg.softuni.autho_moto_manager.service.VehicleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class VehiclesRestController {
    private final VehicleService vehicleService;

    public VehiclesRestController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/api/my-vehicles")
    public List<VehiclesUuidDTO> myVehicles() {

        List<VehiclesUuidDTO> vehicles = vehicleService.getMyVehiclesList();

        return vehicles;
    }

}
