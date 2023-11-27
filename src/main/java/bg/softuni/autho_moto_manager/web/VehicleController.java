package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.binding.SaleDTO;
import bg.softuni.autho_moto_manager.model.dto.view.SaleVehicleView;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.autho_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.autho_moto_manager.service.SaleService;
import bg.softuni.autho_moto_manager.service.UserService;
import bg.softuni.autho_moto_manager.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.autho_moto_manager.util.Constants.BINDING_RESULT_PACKAGE;

@Controller
@RequestMapping(("/vehicle"))
public class VehicleController {

    private final VehicleService vehicleService;
    private final SaleService saleService;
    private final UserService userService;

    public VehicleController(VehicleService vehicleService,
                             SaleService saleService,
                             UserService userService) {
        this.vehicleService = vehicleService;
        this.saleService = saleService;
        this.userService = userService;
    }

    @ModelAttribute("saleDTO")
    public SaleDTO initSaleDTO() {
        return new SaleDTO();
    }

    @GetMapping("/details/{uuid}")
    public String details(Model model, @PathVariable("uuid") String uuid) {
        VehicleDetailsViewDTO vehicle = vehicleService.getDetailsByUuid(uuid);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("automobileType", VehicleTypeEnum.AUTOMOBILE);
        model.addAttribute("costTypes", CostTypeEnum.values());
        model.addAttribute("canModify", userService.hasPermissionToModify(uuid));
        return "vehicle-details";
    }


    @GetMapping("/sell/{uuid}")
    public String sell(Model model,
                       @PathVariable("uuid") String uuid) {

        SaleVehicleView saleView = saleService.getSaleVehicleView(uuid);
        model.addAttribute("saleView", saleView);

        return "sell";
    }

    @PostMapping("/sell/{uuid}")
    public String sell(@PathVariable("uuid") String vehicle,
                       @Valid SaleDTO saleDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("saleDTO", saleDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PACKAGE + "saleDTO", bindingResult);

            return "redirect:/vehicle/sell/" + vehicle;
        }

        saleService.sell(saleDTO);

        return "redirect:/vehicle/details/" + vehicle;

    }
}
