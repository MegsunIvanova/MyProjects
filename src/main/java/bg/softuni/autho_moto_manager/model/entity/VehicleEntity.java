package bg.softuni.autho_moto_manager.model.entity;

import bg.softuni.autho_moto_manager.model.enums.EngineEnum;
import bg.softuni.autho_moto_manager.model.enums.TransmissionEnum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class VehicleEntity extends BaseEntity {

    @ManyToOne(optional = false)
    private ModelEntity model;

    @Column(name = "vin_lot_number", unique = true)
    private String vinLotNumber;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Long odometerInKm;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EngineEnum engine;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransmissionEnum transmission;

    @OneToMany(targetEntity = CostEntity.class, mappedBy = "vehicle")
    private Set<CostEntity> costCalculation;

    @ManyToMany
    @JoinTable(name = "vehicles_owners",
            joinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<UserEntity> owners;

    public VehicleEntity() {
        this.costCalculation = new HashSet<>();
        this.owners = new HashSet<>();
    }

    public ModelEntity getModel() {
        return model;
    }

    public VehicleEntity setModel(ModelEntity model) {
        this.model = model;
        return this;
    }

    public String getVinLotNumber() {
        return vinLotNumber;
    }

    public VehicleEntity setVinLotNumber(String vinLotNumber) {
        this.vinLotNumber = vinLotNumber;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public VehicleEntity setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Long getOdometerInKm() {
        return odometerInKm;
    }

    public VehicleEntity setOdometerInKm(Long odometerInKm) {
        this.odometerInKm = odometerInKm;
        return this;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public VehicleEntity setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public VehicleEntity setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }

    public Set<CostEntity> getCostCalculation() {
        return costCalculation;
    }

    public VehicleEntity setCostCalculation(Set<CostEntity> costCalculation) {
        this.costCalculation = costCalculation;
        return this;
    }

    public Set<UserEntity> getOwners() {
        return owners;
    }

    public VehicleEntity setOwners(Set<UserEntity> owners) {
        this.owners = owners;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof VehicleEntity that)) return false;
        return Objects.equals(getId(),that.getId());
    }

}
