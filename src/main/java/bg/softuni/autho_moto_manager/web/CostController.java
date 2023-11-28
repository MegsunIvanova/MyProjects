package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.UpdateCostDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.DetailedCostsView;
import bg.softuni.autho_moto_manager.service.CostService;
import bg.softuni.autho_moto_manager.service.UserService;
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
    private final UserService userService;

    public CostController(CostService costService, UserService userService) {
        this.costService = costService;
        this.userService = userService;
    }

    @GetMapping("/add/{vehicle}")
    public String addCost(@PathVariable("vehicle") String vehicle,
                          Model model) {
//        model.addAttribute("vehicle", vehicle);
        AddCostViewDTO addCostViewDTO = costService.getAddCostViewDTO();

        model.addAttribute("costTypes", addCostViewDTO.getCostTypes());
        model.addAttribute("currencies", addCostViewDTO.getCurrencies());

        if (!model.containsAttribute("costDTO")) {
            model.addAttribute("costDTO", new AddCostDTO());
        }

        return "add-cost";
    }

    @PostMapping("/add/{vehicle}")
    public String addCost(@PathVariable("vehicle") String vehicle,
                          @Valid AddCostDTO addCostDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("costDTO", addCostDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PACKAGE + "costDTO",
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
        model.addAttribute("canModify", userService.hasPermissionToModify(vehicle));

        return "costs-details";
    }

    @DeleteMapping("/{id}/{vehicle}")
    public String delete(Model model,
                         @PathVariable("id") Long id,
                         @PathVariable ("vehicle") String vehicle) {
        costService.delete(id);

        return "redirect:/costs/more/{vehicle}";
    }

    @GetMapping("/update/{id}/{vehicle}")
    public String updateCost(@PathVariable("id") Long id,
                             @PathVariable("vehicle") String vehicle,
                             Model model) {

        AddCostViewDTO addCostViewDTO = costService.getAddCostViewDTO();
        model.addAttribute("costTypes", addCostViewDTO.getCostTypes());
        model.addAttribute("currencies", addCostViewDTO.getCurrencies());

        if (!model.containsAttribute("costDTO")) {
            UpdateCostDTO costDTO = costService.getUpdateCostDTO(id, vehicle);
            model.addAttribute("costDTO", costDTO);
        }

        return "update-cost";
    }

    @PutMapping("/update/{id}/{vehicle}")
    public String updateCost(@PathVariable("id") Long id,
                             @PathVariable("vehicle") String vehicle,
                             @Valid UpdateCostDTO costDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("costDTO", costDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PACKAGE + "costDTO",
                    bindingResult);

            return "redirect:/costs/update/" + id + "/" + vehicle;
        }

        costService.updateCost(costDTO);

        return "redirect:/costs/more/" + vehicle;
    }

}
