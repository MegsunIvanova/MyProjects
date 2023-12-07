package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.binding.CreateModelDTO;
import bg.softuni.auto_moto_manager.model.entity.MakeEntity;
import bg.softuni.auto_moto_manager.model.entity.ModelEntity;
import bg.softuni.auto_moto_manager.repository.MakeRepository;
import bg.softuni.auto_moto_manager.repository.ModelRepository;
import bg.softuni.auto_moto_manager.service.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(MakeRepository makeRepository, ModelRepository modelRepository, ModelMapper modelMapper) {
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> getAllMakersNamesOrdered() {
        return makeRepository
                .findAllOrdered()
                .stream()
                .map(MakeEntity::getName)
                .toList();
    }

    @Override
    public void createModel(CreateModelDTO createModelDTO) {
        ModelEntity modelEntity = modelMapper.map(createModelDTO, ModelEntity.class);
        modelRepository.save(modelEntity);
    }
}
