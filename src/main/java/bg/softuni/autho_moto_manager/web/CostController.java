package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.DetailedCostsView;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.autho_moto_manager.service.CostService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.autho_moto_manager.util.Constants.BINDING_RESULT_PACKAGE;

@Controller
@RequestMapping("/costs")
public class CostController {
    private final CostService costService;

    public CostController(CostService costService) {
        this.costService = costService;
    }

    @ModelAttribute
    public AddCostDTO initAddCostDTO() {
        return new AddCostDTO();
    }

    @GetMapping("/add/{vehicle}")
    public String addCost(@PathVariable("vehicle") String vehicle,
                          Model model) {
        model.addAttribute("vehicle", vehicle);
        AddCostViewDTO addCostViewDTO = costService.getAddCostViewDTO();

        model.addAttribute("costTypes", addCostViewDTO.getCostTypes());
        model.addAttribute("currencies", addCostViewDTO.getCurrencies());

        return "add-cost";
    }

    @PostMapping("/add")
    public String addCost(@Valid AddCostDTO addCostDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCostDTO", addCostDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PACKAGE + "addCostDTO",
                    bindingResult);

            return "redirect:/costs/add/" + addCostDTO.getVehicle();
        }

        costService.addCost(addCostDTO);

        return "redirect:/vehicle/details/" + addCostDTO.getVehicle();
    }

    @GetMapping("/more/{vehicle}")
    public String costsDetails(@PathVariable("vehicle") String vehicle,
                               Model model) {

        DetailedCostsView detailedCostsView = this.costService.getDetailedCostsView(vehicle);

        model.addAttribute("detailedCostsView", detailedCostsView);

        return "costs-details";
    }



}
