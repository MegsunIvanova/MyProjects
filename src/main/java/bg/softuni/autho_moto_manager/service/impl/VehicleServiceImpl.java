package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.view.AddVehicleViewDTO;
import bg.softuni.autho_moto_manager.model.entity.ModelEntity;
import bg.softuni.autho_moto_manager.repository.MakeRepository;
import bg.softuni.autho_moto_manager.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final MakeRepository makeRepository;

    public VehicleServiceImpl(MakeRepository makeRepository) {
        this.makeRepository = makeRepository;
    }

    @Override
    public AddVehicleViewDTO getAddVehicleView() {
        return new AddVehicleViewDTO(getModelsByMake());
    }

    private Map<String, List<String>> getModelsByMake() {
        Map<String, List<String>> modelsByMake = new LinkedHashMap<>();

        makeRepository.findAllOrdered()
                .forEach(make -> modelsByMake.put(
                        make.getName(),
                        make.getModels().stream().map(ModelEntity::getModel).toList()));

        return modelsByMake;
    }
}
