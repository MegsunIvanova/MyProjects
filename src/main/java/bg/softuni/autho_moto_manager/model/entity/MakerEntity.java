package bg.softuni.autho_moto_manager.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "makers")
public class MakerEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = ModelEntity.class, mappedBy = "maker")
    Set<ModelEntity> models;

    public MakerEntity() {
        this.models = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public MakerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Set<ModelEntity> getModels() {
        return models;
    }

    public MakerEntity setModels(Set<ModelEntity> models) {
        this.models = models;
        return this;
    }
}
