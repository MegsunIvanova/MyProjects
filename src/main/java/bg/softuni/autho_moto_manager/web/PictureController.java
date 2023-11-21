package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.binding.AddPictureDTO;
import bg.softuni.autho_moto_manager.service.PictureService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static bg.softuni.autho_moto_manager.util.Constants.BINDING_RESULT_PACKAGE;

@Controller
@RequestMapping("pictures")
public class PictureController {
    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @ModelAttribute("addPictureDTO")
    public AddPictureDTO initaddPictureDTO() {
        return new AddPictureDTO();
    }

    @GetMapping("/add/{vehicle}")
    public String addPicture(@PathVariable("vehicle") String vehicle,
                             Model model) {
//        model.addAttribute("vehicle", vehicle);

        return "add-picture";
    }

    @PostMapping("/add/{vehicle}")
    public String addPicture(@PathVariable("vehicle") String vehicle,
                             @Valid AddPictureDTO addPictureDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPictureDTO", addPictureDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PACKAGE + "addPictureDTO",
                    bindingResult);

            return "redirect:/pictures/add/" + addPictureDTO.getVehicle();
        }

        pictureService.addPicture(addPictureDTO);

        return "redirect:/vehicle/details/" + addPictureDTO.getVehicle();
    }
}
