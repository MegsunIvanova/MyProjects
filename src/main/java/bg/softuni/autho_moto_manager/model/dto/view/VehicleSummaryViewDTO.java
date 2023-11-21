package bg.softuni.autho_moto_manager.model.dto.view;

import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;

public class VehicleSummaryViewDTO {
    private String uuid;
    private String title;
    private String type;
    private String modelName;
    private String makeName;
    private String vin;
    private Integer year;
    private Integer odometerInKm;
    private String engine;
    private String transmission;
    private String primaryImage;

    public VehicleSummaryViewDTO(VehicleEntity vehicleEntity) {
        this.uuid = vehicleEntity.getUuid();
        this.type = vehicleEntity.getModel().getType().name();
        this.modelName = vehicleEntity.getModel().getName();
        this.makeName = vehicleEntity.getModel().getMake().getName();
        this.vin = vehicleEntity.getVin();
        this.year = vehicleEntity.getYear();
        this.odometerInKm = vehicleEntity.getOdometerInKm();
        this.engine = vehicleEntity.getEngine().name();
        this.transmission = vehicleEntity.getTransmission().name();
        this.primaryImage = vehicleEntity.getPrimaryImage() == null
                ? null
                : vehicleEntity.getPrimaryImage().getUrl();
        this.title = vehicleEntity.getSummaryTitle();
    }

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public String getModelName() {
        return modelName;
    }

    public String getMakeName() {
        return makeName;
    }

    public String getVin() {
        return vin;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getOdometerInKm() {
        return odometerInKm;
    }

    public String getEngine() {
        return engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public String getTitle() {
        return title;
    }
}
