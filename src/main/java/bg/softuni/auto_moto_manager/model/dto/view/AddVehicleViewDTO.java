package bg.softuni.auto_moto_manager.model.dto.view;

import bg.softuni.auto_moto_manager.model.enums.EngineEnum;
import bg.softuni.auto_moto_manager.model.enums.TransmissionEnum;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddVehicleViewDTO {
    private EngineEnum[] engineTypes;
    private TransmissionEnum[] transmissions;
    private Map<String, List<String>> modelsByMake;

    public AddVehicleViewDTO() {
        this.engineTypes = EngineEnum.values();
        this.transmissions = TransmissionEnum.values();
        this.modelsByMake = new LinkedHashMap<>();
    }

    public AddVehicleViewDTO(Map<String, List<String>> modelsByMake) {
        this();
        this.modelsByMake = modelsByMake;
    }

    public EngineEnum[] getEngineTypes() {
        return engineTypes;
    }

    public TransmissionEnum[] getTransmissions() {
        return transmissions;
    }

    public Map<String, List<String>> getModelsByMake() {
        return modelsByMake;
    }

}
