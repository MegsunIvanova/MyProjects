package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.UpdateCostDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.DetailedCostsView;
import bg.softuni.autho_moto_manager.service.CostService;
import bg.softuni.autho_moto_manager.service.ModifyAuthorizeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final ModifyAuthorizeService modifyAuthorizeService;

    public CostController(CostService costService, ModifyAuthorizeService modifyAuthorizeService) {
        this.costService = costService;
        this.modifyAuthorizeService = modifyAuthorizeService;
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal)")
    @GetMapping("/add/{uuid}")
    public String addCost(@PathVariable("uuid") String uuid,
                          @AuthenticationPrincipal UserDetails principal,
                          Model model) {

        AddCostViewDTO addCostViewDTO = costService.getAddCostViewDTO();

        model.addAttribute("costTypes", addCostViewDTO.getCostTypes());
        model.addAttribute("currencies", addCostViewDTO.getCurrencies());

        if (!model.containsAttribute("costDTO")) {
            model.addAttribute("costDTO", new AddCostDTO());
        }

        return "add-cost";
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal)")
    @PostMapping("/add/{uuid}")
    public String addCost(@PathVariable("uuid") String uuid,
                          @AuthenticationPrincipal UserDetails principal,
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

        return "redirect:/vehicle/details/" + uuid;
    }

    @GetMapping("/more/{uuid}")
    public String costsDetails(@PathVariable("uuid") String uuid,
                               @AuthenticationPrincipal UserDetails principal,
                               Model model) {

        DetailedCostsView detailedCostsView = this.costService.getDetailedCostsView(uuid);

        model.addAttribute("detailedCostsView", detailedCostsView);
        model.addAttribute("canModify", modifyAuthorizeService.isPermitted(uuid, principal));

        return "costs-details";
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal, #id)")
    @DeleteMapping("/{id}/{uuid}")
    public String delete(@PathVariable("id") Long id,
                         @PathVariable("uuid") String uuid,
                         @AuthenticationPrincipal UserDetails principal) {

        costService.delete(id);

        return "redirect:/costs/more/{uuid}";
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal, #id)")
    @GetMapping("/update/{id}/{uuid}")
    public String updateCost(@PathVariable("id") Long id,
                             @PathVariable("uuid") String uuid,
                             @AuthenticationPrincipal UserDetails principal,
                             Model model) {

        AddCostViewDTO addCostViewDTO = costService.getAddCostViewDTO();
        model.addAttribute("costTypes", addCostViewDTO.getCostTypes());
        model.addAttribute("currencies", addCostViewDTO.getCurrencies());

        if (!model.containsAttribute("costDTO")) {
            UpdateCostDTO costDTO = costService.getUpdateCostDTO(id, uuid);
            model.addAttribute("costDTO", costDTO);
        }

        return "update-cost";
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal, #id)")
    @PutMapping("/update/{id}/{uuid}")
    public String updateCost(@PathVariable("id") Long id,
                             @PathVariable("uuid") String uuid,
                             @AuthenticationPrincipal UserDetails principal,
                             @Valid UpdateCostDTO costDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("costDTO", costDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PACKAGE + "costDTO",
                    bindingResult);

            return String.format("%s%d%s%s",
                    "redirect:/costs/update/", id, "/", uuid);
        }

        costService.updateCost(costDTO);

        return "redirect:/costs/more/" + uuid;
    }
}
