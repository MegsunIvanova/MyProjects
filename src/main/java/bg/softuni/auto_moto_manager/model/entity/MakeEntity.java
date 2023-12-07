package bg.softuni.auto_moto_manager.model.entity;

import jakarta.persistence.*;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "makes")
@NamedEntityGraph(
        name = "makeWithModels",
        attributeNodes = @NamedAttributeNode("models")
)
public class MakeEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = ModelEntity.class, mappedBy = "make")
    Set<ModelEntity> models;

    public MakeEntity() {
        this.models = new TreeSet<>(Comparator.comparing(ModelEntity::getName));
    }

    public MakeEntity(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MakeEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Set<ModelEntity> getModels() {
        return models;
    }

    public MakeEntity setModels(Set<ModelEntity> models) {
        this.models = models;
        return this;
    }
}
