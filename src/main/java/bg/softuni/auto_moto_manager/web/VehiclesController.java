package bg.softuni.auto_moto_manager.web;

import bg.softuni.auto_moto_manager.model.dto.binding.CreateVehicleDTO;
import bg.softuni.auto_moto_manager.model.dto.view.AddVehicleViewDTO;
import bg.softuni.auto_moto_manager.model.dto.view.VehicleSummaryViewDTO;
import bg.softuni.auto_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.auto_moto_manager.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.auto_moto_manager.util.Constants.BINDING_RESULT_PACKAGE;

@Controller
@RequestMapping("/vehicles")
public class VehiclesController {
    private final VehicleService vehicleService;

    public VehiclesController(VehicleService vehicleService) {
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
                         @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                         @PageableDefault(size = 3, sort = "year", direction = Sort.Direction.DESC)
                         Pageable pageable) {

        model.addAttribute("automobileType", VehicleTypeEnum.AUTOMOBILE);

        Page<VehicleSummaryViewDTO> page =
                vehicleService.getAllVehicles(pageable.withPage(pageNumber));

        model.addAttribute("vehicles", page);

        return "vehicles-all";
    }

}
