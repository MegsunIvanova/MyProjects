package bg.softuni.autho_moto_manager.model.dto.binding;

import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;

public class UserRegisterDTO {
    private String name;
    private String email;
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
