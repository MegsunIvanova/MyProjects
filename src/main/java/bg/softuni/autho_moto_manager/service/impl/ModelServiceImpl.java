package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.CreateModelDTO;
import bg.softuni.autho_moto_manager.model.entity.MakerEntity;
import bg.softuni.autho_moto_manager.model.entity.ModelEntity;
import bg.softuni.autho_moto_manager.repository.MakerRepository;
import bg.softuni.autho_moto_manager.repository.ModelRepository;
import bg.softuni.autho_moto_manager.service.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private final MakerRepository makerRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(MakerRepository makerRepository, ModelRepository modelRepository, ModelMapper modelMapper) {
        this.makerRepository = makerRepository;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> getAllMakersNamesOrdered() {
        return makerRepository
                .findAllOrdered()
                .stream()
                .map(MakerEntity::getName)
                .toList();
    }

    @Override
    public void createModel(CreateModelDTO createModelDTO) {
        ModelEntity modelEntity = modelMapper.map(createModelDTO, ModelEntity.class);
        modelRepository.save(modelEntity);
    }
}
