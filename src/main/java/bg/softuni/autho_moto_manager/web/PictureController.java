package bg.softuni.autho_moto_manager.web;

import bg.softuni.autho_moto_manager.model.dto.binding.AddPictureDTO;
import bg.softuni.autho_moto_manager.service.PictureService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal)")
    @GetMapping("/add/{uuid}")
    public String addPicture(@PathVariable("uuid") String uuid,
                             @AuthenticationPrincipal UserDetails principal) {

        return "add-picture";
    }

    @PreAuthorize("@modifyAuthorizeServiceImpl.isPermitted(#uuid, #principal)")
    @PostMapping("/add/{uuid}")
    public String addPicture(@PathVariable("uuid") String uuid,
                             @AuthenticationPrincipal UserDetails principal,
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
