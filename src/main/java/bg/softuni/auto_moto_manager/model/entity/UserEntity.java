package bg.softuni.auto_moto_manager.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false) //
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<RoleEntity> roles;

    @OneToMany(targetEntity = VehicleEntity.class, mappedBy = "owner")
    private Set<VehicleEntity> vehicles;

    public UserEntity() {
        this.vehicles = new HashSet<>();
        this.roles = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<VehicleEntity> getVehicles() {
        return vehicles;
    }

    public UserEntity setVehicles(Set<VehicleEntity> vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public UserEntity addRole(RoleEntity role) {
        this.roles.add(role);
        return this;
    }

    public UserEntity removeRole(RoleEntity role) {
        this.roles.remove(role);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof UserEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }
}
