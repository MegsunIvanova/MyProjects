package bg.softuni.auto_moto_manager.model.entity;

import bg.softuni.auto_moto_manager.model.enums.EngineEnum;
import bg.softuni.auto_moto_manager.model.enums.TransmissionEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "vehicles")
@NamedEntityGraph(
        name = "vehicleWithCosts",
        attributeNodes = {
                @NamedAttributeNode("costCalculation"),
                @NamedAttributeNode("model"),
                @NamedAttributeNode("primaryImage")}
)
public class VehicleEntity extends BaseEntity {

    @ManyToOne(optional = false)
    private ModelEntity model;

    @Column(name = "vin", unique = true)
    private String vin;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "odometer_km")
    private Integer odometerInKm;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EngineEnum engine;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransmissionEnum transmission;

    private String notes;

    @OneToOne(mappedBy = "vehicle")
    private SaleEntity sale;

    @OneToMany(mappedBy = "vehicle", targetEntity = PictureEntity.class)
    private Set<PictureEntity> pictures;

    @OneToOne
    private PictureEntity primaryImage;

    @OneToMany(targetEntity = CostEntity.class, mappedBy = "vehicle")
    private Set<CostEntity> costCalculation;

    @ManyToOne(optional = false)
    private UserEntity owner;

    @Column(nullable = false, unique = true)
    private String uuid;

    public VehicleEntity() {
        this.costCalculation = new HashSet<>();
        this.pictures = new HashSet<>();
    }

    public ModelEntity getModel() {
        return model;
    }

    public VehicleEntity setModel(ModelEntity model) {
        this.model = model;
        return this;
    }

    public String getVin() {
        return vin;
    }

    public VehicleEntity setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public VehicleEntity setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getOdometerInKm() {
        return odometerInKm;
    }

    public VehicleEntity setOdometerInKm(Integer odometerInKm) {
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

    public String getNotes() {
        return notes;
    }

    public VehicleEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public VehicleEntity setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public PictureEntity getPrimaryImage() {
        return primaryImage;
    }

    public VehicleEntity setPrimaryImage(PictureEntity primaryImage) {
        this.primaryImage = primaryImage;
        return this;
    }

    public Set<CostEntity> getCostCalculation() {
        return costCalculation;
    }

    public VehicleEntity setCostCalculation(Set<CostEntity> costCalculation) {
        this.costCalculation = costCalculation;
        return this;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public VehicleEntity setOwner(UserEntity owner) {
        this.owner = owner;
        return this;
    }

    public SaleEntity getSale() {
        return sale;
    }

    public VehicleEntity setSale(SaleEntity sale) {
        this.sale = sale;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public VehicleEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public int countPictures() {
        return this.pictures.size();
    }

    public String getSummaryTitle() {
        return String.format("%d %s %s", year, model.getMake().getName(), model.getName());
    }

    public BigDecimal getTotalCostsInBGN() {
        return costCalculation.stream()
                .map(CostEntity::getAmountInBGN)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean allCostsCompleted() {
        return costCalculation.stream().allMatch(CostEntity::isCompleted);
    }

}
