package bg.softuni.autho_moto_manager.controller;

import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.autho_moto_manager.service.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/users")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegisterDTO")
    public UserRegisterDTO init() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute(
                    BINDING_RESULT_PACKAGE + "userRegisterDTO",
                    bindingResult);

            return "redirect:/users/register";
        }
        //TODO: validation and errors handling
        userService.register(userRegisterDTO);

        return "redirect:/login";
    }

    @GetMapping("/login")
    private String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailure(
            @ModelAttribute("email") String email,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", true);

        return "login";
    }

}
