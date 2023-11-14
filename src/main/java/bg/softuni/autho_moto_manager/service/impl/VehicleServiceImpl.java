package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.CreateVehicleDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddVehicleViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleSummaryViewDTO;
import bg.softuni.autho_moto_manager.model.entity.ModelEntity;
import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import bg.softuni.autho_moto_manager.repository.MakeRepository;
import bg.softuni.autho_moto_manager.repository.VehicleRepository;
import bg.softuni.autho_moto_manager.service.VehicleService;
import bg.softuni.autho_moto_manager.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final MakeRepository makeRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleServiceImpl(MakeRepository makeRepository,
                              VehicleRepository vehicleRepository,
                              ModelMapper modelMapper) {
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
    public Page<VehicleSummaryViewDTO> getAllVehicles(Pageable page) {
        return vehicleRepository
                .findAll(page)
                .map(VehicleSummaryViewDTO::new);
    }

    @Override
    @Transactional
    public VehicleDetailsViewDTO getDetailsByUuid(String uuid) {
        return vehicleRepository
                .findByUuid(uuid)
                .map(VehicleDetailsViewDTO::new)
                .orElseThrow(() -> new ObjectNotFoundException("Vehicle with uuid " + uuid + " can not be found!"));
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
