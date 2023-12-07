package bg.softuni.auto_moto_manager.model.entity;

import bg.softuni.auto_moto_manager.model.enums.UserRoleEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserRoleEnum role;

    public RoleEntity() {
    }

    public RoleEntity(UserRoleEnum role) {
        this.role = role;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public RoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof RoleEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }
}
