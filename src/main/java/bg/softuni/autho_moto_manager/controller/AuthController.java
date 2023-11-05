package bg.softuni.autho_moto_manager.controller;

import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.autho_moto_manager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    private String register() {
        return "register";
    }

    @PostMapping("/register")
    private String register(UserRegisterDTO userRegisterDTO) {
        //TODO: validation
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
