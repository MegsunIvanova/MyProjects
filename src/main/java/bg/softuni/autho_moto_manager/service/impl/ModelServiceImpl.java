package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.entity.MakerEntity;
import bg.softuni.autho_moto_manager.repository.MakerRepository;
import bg.softuni.autho_moto_manager.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private final MakerRepository makerRepository;

    public ModelServiceImpl(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    @Override
    public List<String> getAllMakersNamesOrdered() {
        return makerRepository
                .findAllOrdered()
                .stream()
                .map(MakerEntity::getName)
                .toList();
    }
}
