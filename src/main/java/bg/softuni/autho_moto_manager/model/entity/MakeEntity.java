package bg.softuni.autho_moto_manager.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "makes")
public class MakeEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = ModelEntity.class, mappedBy = "maker")
    Set<ModelEntity> models;

    public MakeEntity() {
        this.models = new HashSet<>();
    }
}
