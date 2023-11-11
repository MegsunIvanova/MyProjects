package bg.softuni.autho_moto_manager.model.dto.binding;

import bg.softuni.autho_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.autho_moto_manager.model.validation.UniqueModelName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateModelDTO {
    @NotEmpty(message = "Model cannot be empty!")
    @Size(max = 30, message = "Model length must be maximum 30 symbols!")
    @UniqueModelName
    private String name;

    @NotNull(message = "Please, select a type!")
    private VehicleTypeEnum type;

    @NotEmpty(message = "Please, fill in maker!")
    private String make;

    public CreateModelDTO() {
    }

    public String getName() {
        return name;
    }

    public CreateModelDTO setName(String name) {
        this.name = name;
        return this;
    }

    public VehicleTypeEnum getType() {
        return type;
    }

    public CreateModelDTO setType(VehicleTypeEnum type) {
        this.type = type;
        return this;
    }

    public String getMake() {
        return make;
    }

    public CreateModelDTO setMake(String make) {
        this.make = make;
        return this;
    }
}
