package bg.softuni.autho_moto_manager.controller;

import bg.softuni.autho_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.autho_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.autho_moto_manager.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(("/vehicle"))
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        VehicleDetailsViewDTO vehicle = vehicleService.getDetailsById(id);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("automobileType", VehicleTypeEnum.AUTOMOBILE);

        return "vehicle-details";
    }
}
