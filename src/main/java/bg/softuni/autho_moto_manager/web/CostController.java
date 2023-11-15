package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/costs")
public class CostController {
    @ModelAttribute
    public AddCostDTO initAddCostDTO() {
        return new AddCostDTO();
    }

    @GetMapping("/add/{vehicle}")
    public String addCost(@PathVariable("vehicle") String vehicle,
                          Model model) {
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("costTypes", CostTypeEnum.values());

        return "add-cost";
    }

}
