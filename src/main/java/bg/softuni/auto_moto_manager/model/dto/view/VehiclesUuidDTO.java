package bg.softuni.auto_moto_manager.model.dto.view;
public class VehiclesUuidDTO {
    private String uuid;
    private String title;

    public VehiclesUuidDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public VehiclesUuidDTO setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public VehiclesUuidDTO setTitle(String title) {
        this.title = title;
        return this;
    }
}
