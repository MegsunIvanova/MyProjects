package bg.softuni.autho_moto_manager.model.dto.binding;

import bg.softuni.autho_moto_manager.model.enums.EngineEnum;
import bg.softuni.autho_moto_manager.model.enums.TransmissionEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class CreateVehicleDTO {

    @NotEmpty
    private String model;

    @NotEmpty
    @Pattern(regexp = "/(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z0-9]{17}/ig")
    //TODO: Unique and others constraints
    private String vin;

    @NotNull
    //TODO: Between 1190 and current Year
    private Integer year;

    @Positive
    private Integer odometerInKm;

    @NotNull
    private EngineEnum engine;

    @NotNull
    private TransmissionEnum transmission;

    public CreateVehicleDTO() {
    }

    public String getModel() {
        return model;
    }

    public CreateVehicleDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getVin() {
        return vin;
    }

    public CreateVehicleDTO setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public CreateVehicleDTO setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getOdometerInKm() {
        return odometerInKm;
    }

    public CreateVehicleDTO setOdometerInKm(Integer odometerInKm) {
        this.odometerInKm = odometerInKm;
        return this;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public CreateVehicleDTO setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public CreateVehicleDTO setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }
}
