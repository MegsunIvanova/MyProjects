package bg.softuni.auto_moto_manager.service;

import bg.softuni.auto_moto_manager.model.dto.binding.CreateModelDTO;

import java.util.List;

public interface ModelService {

    List<String> getAllMakersNamesOrdered();

    void createModel(CreateModelDTO createModelDTO);
}
