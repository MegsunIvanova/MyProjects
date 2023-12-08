package bg.softuni.auto_moto_manager.web;

import bg.softuni.auto_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.auto_moto_manager.model.dto.binding.UserEditDTO;
import bg.softuni.auto_moto_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static bg.softuni.auto_moto_manager.util.Constants.BINDING_RESULT_PACKAGE;
import static bg.softuni.auto_moto_manager.util.Constants.EMAIL_REGEX;

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

        userService.register(userRegisterDTO);

        return "redirect:/users/login";
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

    @GetMapping("/edit/roles")
    public String editRoles(@RequestParam(value = "emailToFind", required = false) String emailToFind,
                            Model model) {

        Optional<UserEditDTO> optionalUser = emailToFind != null && emailToFind.matches(EMAIL_REGEX)
                ? userService.getUserForEdit(emailToFind)
                : Optional.empty();

        String errorMsg = emailToFind != null && optionalUser.isEmpty()
                ? "User with email \"" + emailToFind + "\" was not found!"
                : null;

        UserEditDTO userEditDTO = optionalUser.orElseGet(UserEditDTO::new);

        model.addAttribute("userEditDTO", userEditDTO);
        model.addAttribute("error", errorMsg);

        return "users-edit";
    }

    @PostMapping("/edit/roles")
    public String editUserRoles(UserEditDTO userEditDTO) {
        userService.editRoles(userEditDTO);
        return "redirect:/";
    }

}
