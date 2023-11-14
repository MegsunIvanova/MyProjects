package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.AddPictureDTO;
import bg.softuni.autho_moto_manager.model.entity.PictureEntity;
import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import bg.softuni.autho_moto_manager.repository.PictureRepository;
import bg.softuni.autho_moto_manager.repository.VehicleRepository;
import bg.softuni.autho_moto_manager.service.PictureService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {
    private final ModelMapper modelMapper;
    private final PictureRepository pictureRepository;
    private final VehicleRepository vehicleRepository;

    public PictureServiceImpl(ModelMapper modelMapper, PictureRepository pictureRepository, VehicleRepository vehicleRepository) {
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional
    public void addPicture(AddPictureDTO addPictureDTO) {
        PictureEntity picture = modelMapper.map(addPictureDTO, PictureEntity.class);

        pictureRepository.save(picture);

        VehicleEntity vehicle = picture.getVehicle();

        if (addPictureDTO.isPrimary() || vehicle.countPictures() <= 1) {
            vehicle.setPrimaryImage(picture);
            vehicleRepository.save(vehicle);
        }


    }
}
