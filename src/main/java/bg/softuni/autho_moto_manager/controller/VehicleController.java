package bg.softuni.autho_moto_manager.controller;

import bg.softuni.autho_moto_manager.model.dto.binding.CreateVehicleDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddVehicleViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleSummaryViewDTO;
import bg.softuni.autho_moto_manager.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.autho_moto_manager.util.Constants.BINDING_RESULT_PACKAGE;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @ModelAttribute("createVehicleDTO")
    public CreateVehicleDTO initCreateVehicleDTO() {
        return new CreateVehicleDTO();
    }

    @GetMapping("/add")
    public String createVehicle(Model model) {
        AddVehicleViewDTO addVehicleView = this.vehicleService.getAddVehicleView();
        model.addAttribute("engineTypes", addVehicleView.getEngineTypes());
        model.addAttribute("transmissions", addVehicleView.getTransmissions());
        model.addAttribute("modelsByMake", addVehicleView.getModelsByMake());

        return "add-vehicle";
    }

    @PostMapping("/add")
    public String createVehicle(@Valid CreateVehicleDTO createVehicleDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createVehicleDTO", createVehicleDTO);
            redirectAttributes.addFlashAttribute(
                    BINDING_RESULT_PACKAGE + "createVehicleDTO",
                    bindingResult);

            return "redirect:/vehicles/add";
        }

        vehicleService.create(createVehicleDTO);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String getAll(Model model,
                         @PageableDefault(size = 3, sort = "id")
                         Pageable pageable) {

        Page<VehicleSummaryViewDTO> allVehicles = vehicleService.getAllVehicles(pageable);

        model.addAttribute("vehicles", allVehicles);

        return "vehicles-all";
    }

}
