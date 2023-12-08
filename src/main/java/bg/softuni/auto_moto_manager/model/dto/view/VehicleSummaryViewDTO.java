package bg.softuni.auto_moto_manager.model.dto.view;

import bg.softuni.auto_moto_manager.model.entity.VehicleEntity;

public class VehicleSummaryViewDTO {
    private final String uuid;
    private final String title;
    private final String type;
    private final String modelName;
    private final String makeName;
    private final String vin;
    private final Integer year;
    private final Integer odometerInKm;
    private final String engine;
    private final String transmission;
    private final String primaryImage;
    private final String ownerName;

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
        this.ownerName = vehicleEntity.getOwner().getName();
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

    public String getOwnerName() {
        return ownerName;
    }
}
