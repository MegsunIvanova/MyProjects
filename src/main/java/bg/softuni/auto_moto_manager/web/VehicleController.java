package bg.softuni.auto_moto_manager.web;

import bg.softuni.auto_moto_manager.model.dto.binding.SaleDTO;
import bg.softuni.auto_moto_manager.model.dto.view.SaleVehicleView;
import bg.softuni.auto_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.auto_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.auto_moto_manager.service.ModifyAuthorizeService;
import bg.softuni.auto_moto_manager.service.SaleService;
import bg.softuni.auto_moto_manager.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.auto_moto_manager.util.Constants.BINDING_RESULT_PACKAGE;

@Controller
@RequestMapping(("/vehicle"))
public class VehicleController {

    private final VehicleService vehicleService;
    private final SaleService saleService;
    private final ModifyAuthorizeService modifyAuthorizeService;

    public VehicleController(VehicleService vehicleService,
                             SaleService saleService,
                             ModifyAuthorizeService modifyAuthorizeService) {
        this.vehicleService = vehicleService;
        this.saleService = saleService;
        this.modifyAuthorizeService = modifyAuthorizeService;
    }

    @ModelAttribute("saleDTO")
    public SaleDTO initSaleDTO() {
        return new SaleDTO();
    }

    @GetMapping("/details/{uuid}")
    public String details(Model model,
                          @PathVariable("uuid") String uuid,
                          @AuthenticationPrincipal UserDetails principal) {

        VehicleDetailsViewDTO vehicle = vehicleService.getDetailsByUuid(uuid);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("automobileType", VehicleTypeEnum.AUTOMOBILE);
        model.addAttribute("costTypes", CostTypeEnum.values());
        model.addAttribute("canModify", modifyAuthorizeService.isPermitted(uuid, principal));

        return "vehicle-details";
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal)")
    @GetMapping("/sell/{uuid}")
    public String sell(Model model,
                       @PathVariable("uuid") String uuid,
                       @AuthenticationPrincipal UserDetails principal) {

        SaleVehicleView saleView = saleService.getSaleVehicleView(uuid);
        model.addAttribute("saleView", saleView);

        return "sell";
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal)")
    @PostMapping("/sell/{uuid}")
    public String sell(@PathVariable("uuid") String uuid,
                       @AuthenticationPrincipal UserDetails principal,
                       @Valid SaleDTO saleDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("saleDTO", saleDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PACKAGE + "saleDTO", bindingResult);

            return "redirect:/vehicle/sell/" + uuid;
        }

        saleService.sell(saleDTO);

        return "redirect:/vehicle/details/" + uuid;
    }
}
