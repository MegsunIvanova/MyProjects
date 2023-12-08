package bg.softuni.auto_moto_manager.model.dto.binding;

public class UserEditDTO {
    private String name;
    private String email;
    private boolean admin;

    public UserEditDTO() {
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

    public boolean isAdmin() {
        return admin;
    }

    public UserEditDTO setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }
}
