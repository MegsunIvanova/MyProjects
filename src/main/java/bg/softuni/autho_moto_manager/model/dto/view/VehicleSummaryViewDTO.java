package bg.softuni.autho_moto_manager.model.dto.view;

public class VehicleSummaryViewDTO {
    private Long id;
    private String modelName;
    private String makeName;
    private String vin;
    private Integer year;
    private Integer odometerInKm;
    private String engine;
    private String transmission;
    private String primaryImage;

    public VehicleSummaryViewDTO() {
    }

    public Long getId() {
        return id;
    }

    public VehicleSummaryViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public VehicleSummaryViewDTO setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public String getMakeName() {
        return makeName;
    }

    public VehicleSummaryViewDTO setMakeName(String makeName) {
        this.makeName = makeName;
        return this;
    }

    public String getVin() {
        return vin;
    }

    public VehicleSummaryViewDTO setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public VehicleSummaryViewDTO setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getOdometerInKm() {
        return odometerInKm;
    }

    public VehicleSummaryViewDTO setOdometerInKm(Integer odometerInKm) {
        this.odometerInKm = odometerInKm;
        return this;
    }

    public String getEngine() {
        return engine;
    }

    public VehicleSummaryViewDTO setEngine(String engine) {
        this.engine = engine;
        return this;
    }

    public String getTransmission() {
        return transmission;
    }

    public VehicleSummaryViewDTO setTransmission(String transmission) {
        this.transmission = transmission;
        return this;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public VehicleSummaryViewDTO setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
        return this;
    }

    public  String getSummaryTitle () {
        return String.format("%d %s %s", year, makeName, modelName);
    }
}
