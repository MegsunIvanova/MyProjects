package bg.softuni.autho_moto_manager.model.dto.binding;

import bg.softuni.autho_moto_manager.model.enums.EngineEnum;
import bg.softuni.autho_moto_manager.model.enums.TransmissionEnum;
import bg.softuni.autho_moto_manager.model.validation.PresentOrPastYear;
import bg.softuni.autho_moto_manager.model.validation.UniqueVIN;
import jakarta.validation.constraints.*;

public class CreateVehicleDTO {

    @NotEmpty(message = "Please, select a model!")
    private String model;

    @Pattern(regexp = "/(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z0-9]{17}/ig", message = "Invalid VIN format!")
    @UniqueVIN(message = "VIN must be unique!")
    private String vin;

    @NotNull (message = "Year must be filled in!")
    @Min(value = 1995, message = "Year must be after 1195!")
    @PresentOrPastYear
    private Integer year;

    @Positive (message = "Odometer must be positive!")
    @Max(value = 400000, message = "Odometer must up to 400000 km!")
    private Integer odometerInKm;

    @NotNull(message = "Please, select engine type!")
    private EngineEnum engine;

    @NotNull(message = "Please, select transmission!")
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
