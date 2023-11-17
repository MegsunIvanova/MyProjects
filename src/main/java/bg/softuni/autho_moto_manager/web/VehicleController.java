package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
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

    @GetMapping("/details/{uuid}")
    public String details(Model model, @PathVariable("uuid") String uuid) {
        VehicleDetailsViewDTO vehicle = vehicleService.getDetailsByUuid(uuid);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("automobileType", VehicleTypeEnum.AUTOMOBILE);
        model.addAttribute("costTypes", CostTypeEnum.values());

        return "vehicle-details";
    }
}
