package bg.softuni.auto_moto_manager.model.dto.binding;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import static bg.softuni.auto_moto_manager.util.Constants.URL_REGEX;

public class AddPictureDTO {
    @NotEmpty
    @URL(regexp =URL_REGEX)
    private String url;
    private String vehicle;
    private boolean primary;

    public AddPictureDTO() {
    }

    public String getUrl() {
        return url;
    }

    public AddPictureDTO setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getVehicle() {
        return vehicle;
    }

    public AddPictureDTO setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public boolean isPrimary() {
        return primary;
    }

    public AddPictureDTO setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }
}
