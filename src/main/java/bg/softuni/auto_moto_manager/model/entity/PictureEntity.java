package bg.softuni.auto_moto_manager.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String url;

    @ManyToOne
    private VehicleEntity vehicle;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public PictureEntity setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof PictureEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }
}
