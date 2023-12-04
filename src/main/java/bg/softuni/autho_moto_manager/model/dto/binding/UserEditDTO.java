package bg.softuni.autho_moto_manager.model.dto.binding;

import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;

import java.util.HashSet;
import java.util.Set;

public class UserEditDTO {
    private String name;
    private String email;
//    private Set<UserRoleEnum> roles;
    private boolean admin;

    public UserEditDTO() {
//        this.roles = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public UserEditDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditDTO setEmail(String email) {
        this.email = email;
        return this;
    }

//    public Set<UserRoleEnum> getRoles() {
//        return roles;
//    }

//    public UserEditDTO setRoles(Set<UserRoleEnum> roles) {
//        this.roles = roles;
//        setAdmin(roles.contains(UserRoleEnum.ADMIN));
//        return this;
//    }

    public boolean isAdmin() {
        return admin;
    }

    public UserEditDTO setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }
}
