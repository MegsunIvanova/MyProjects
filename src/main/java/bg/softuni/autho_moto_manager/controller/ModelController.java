package bg.softuni.autho_moto_manager.controller;

import bg.softuni.autho_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.autho_moto_manager.service.ModelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/models")
public class ModelController {
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/add")
    public String createModel(Model model) {
        model.addAttribute("vehicleTypes", VehicleTypeEnum.values());

        List<String> makersNames = modelService.getAllMakersNamesOrdered();
        model.addAttribute("makersNames", makersNames);

        return "add-vehicle-model";
    }


}
