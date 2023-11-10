package bg.softuni.autho_moto_manager.model.entity;

import bg.softuni.autho_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.autho_moto_manager.model.validation.UniqueModelName;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleTypeEnum type;

    @ManyToOne(optional = false)
    private MakerEntity maker;

    public ModelEntity() {
    }

    public String getModel() {
        return model;
    }

    public ModelEntity setModel(String model) {
        this.model = model;
        return this;
    }

    public VehicleTypeEnum getType() {
        return type;
    }

    public ModelEntity setType(VehicleTypeEnum type) {
        this.type = type;
        return this;
    }

    public MakerEntity getMaker() {
        return maker;
    }

    public ModelEntity setMaker(MakerEntity maker) {
        this.maker = maker;
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
