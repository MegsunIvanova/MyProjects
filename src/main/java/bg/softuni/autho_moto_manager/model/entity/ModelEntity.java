package bg.softuni.autho_moto_manager.model.entity;

import bg.softuni.autho_moto_manager.model.enums.VehicleTypeEnum;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleTypeEnum type;

    @ManyToOne(optional = false)
    private MakeEntity make;

    public ModelEntity() {
    }

    public String getName() {
        return name;
    }

    public ModelEntity setName(String model) {
        this.name = model;
        return this;
    }

    public VehicleTypeEnum getType() {
        return type;
    }

    public ModelEntity setType(VehicleTypeEnum type) {
        this.type = type;
        return this;
    }

    public MakeEntity getMake() {
        return make;
    }

    public ModelEntity setMake(MakeEntity maker) {
        this.make = maker;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ModelEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
