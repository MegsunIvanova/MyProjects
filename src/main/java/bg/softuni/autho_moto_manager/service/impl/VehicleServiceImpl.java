package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.CreateVehicleDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddVehicleViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleSummaryViewDTO;
import bg.softuni.autho_moto_manager.model.entity.ModelEntity;
import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import bg.softuni.autho_moto_manager.repository.MakeRepository;
import bg.softuni.autho_moto_manager.repository.VehicleRepository;
import bg.softuni.autho_moto_manager.service.VehicleService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final MakeRepository makeRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleServiceImpl(MakeRepository makeRepository, VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.makeRepository = makeRepository;
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddVehicleViewDTO getAddVehicleView() {
        return new AddVehicleViewDTO(getModelsByMake());
    }

    @Override
    public void create(CreateVehicleDTO createVehicleDTO) {
        VehicleEntity vehicleEntity = modelMapper.map(createVehicleDTO, VehicleEntity.class);
        vehicleRepository.save(vehicleEntity);
    }

    @Override
    @Transactional
    public Page<VehicleSummaryViewDTO> getAllVehicles(Pageable pageable) {
        return vehicleRepository
                .findAll(pageable)
                .map(VehicleServiceImpl::mapAsSummary);
    }

    private static VehicleSummaryViewDTO mapAsSummary(VehicleEntity vehicleEntity) {
        return new VehicleSummaryViewDTO()
                .setId(vehicleEntity.getId())
                .setModelName(vehicleEntity.getModel().getName())
                .setMakeName(vehicleEntity.getModel().getMake().getName())
                .setVin(vehicleEntity.getVin())
                .setYear(vehicleEntity.getYear())
                .setOdometerInKm(vehicleEntity.getOdometerInKm())
                .setEngine(vehicleEntity.getEngine().name())
                .setTransmission(vehicleEntity.getTransmission().name())
                .setPrimaryImage(vehicleEntity.getPrimaryImage().getUrl());
    }

    private Map<String, List<String>> getModelsByMake() {
        Map<String, List<String>> modelsByMake = new LinkedHashMap<>();

        makeRepository.findAllOrdered()
                .forEach(make -> modelsByMake.put(
                        make.getName(),
                        make.getModels().stream().map(ModelEntity::getName).toList()));

        return modelsByMake;
    }
}
