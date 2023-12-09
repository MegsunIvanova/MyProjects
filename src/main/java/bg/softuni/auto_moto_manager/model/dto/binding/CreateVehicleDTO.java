package bg.softuni.auto_moto_manager.model.dto.binding;

import bg.softuni.auto_moto_manager.model.enums.EngineEnum;
import bg.softuni.auto_moto_manager.model.enums.TransmissionEnum;
import bg.softuni.auto_moto_manager.model.validation.PresentOrPastYear;
import bg.softuni.auto_moto_manager.model.validation.UniqueVIN;
import jakarta.validation.constraints.*;

import java.util.UUID;

public class CreateVehicleDTO {

    @NotEmpty(message = "Please, select a model!")
    private String model;

    @Pattern(regexp = "^$|(^(?=.*[0-9])(?=.*[A-z])[0-9A-z-]{17}$)", message = "Invalid VIN format!")
    @UniqueVIN(message = "VIN must be unique!")
    private String vin;

    @NotNull (message = "Year must be filled in!")
    @Min(value = 1995, message = "Year must be after 1195!")
    @PresentOrPastYear
    private Integer year;

    @PositiveOrZero (message = "Odometer must be positive or zero!")
    @Max(value = 400000, message = "Odometer must  be 400000 km maximum!")
    private Integer odometerInKm;

    @NotNull(message = "Please, select engine type!")
    private EngineEnum engine;

    @NotNull(message = "Please, select transmission!")
    private TransmissionEnum transmission;

    private String notes;

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

    public String getNotes() {
        return notes;
    }

    public CreateVehicleDTO setNotes(String notes) {
        this.notes = notes;
        return this;
    }

}
