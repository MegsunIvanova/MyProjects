package bg.softuni.autho_moto_manager.model.dto.binding;

import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.model.validation.FieldsMatch;
import bg.softuni.autho_moto_manager.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

@FieldsMatch(first = "password", second = "confirmPassword", message = "Passwords do not match!")
public class UserRegisterDTO {
    @NotEmpty(message = "Name cannot be empty!")
    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 symbols!")
    private String name;
    @NotEmpty(message = "Email cannot be empty!")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email!")
    @UniqueUserEmail
    private String email;
    @NotEmpty(message = "Password cannot be empty!")
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 symbols!")
    private String password;
    private String confirmPassword;
    private UserRoleEnum role;

    public UserRegisterDTO() {
    }

    public String getName() {
        return name;
    }

    public UserRegisterDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRegisterDTO setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
