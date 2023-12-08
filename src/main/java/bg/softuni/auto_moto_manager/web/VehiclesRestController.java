package bg.softuni.auto_moto_manager.web;

import bg.softuni.auto_moto_manager.model.dto.view.VehiclesUuidDTO;
import bg.softuni.auto_moto_manager.service.VehicleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class VehiclesRestController {
    private final VehicleService vehicleService;

    public VehiclesRestController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/api/my-vehicles")
    public List<VehiclesUuidDTO> myVehicles(@AuthenticationPrincipal UserDetails principal) {

        return vehicleService.getMyVehiclesList(principal);
    }

}
