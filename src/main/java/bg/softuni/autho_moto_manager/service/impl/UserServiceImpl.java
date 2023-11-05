package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.autho_moto_manager.model.entity.UserEntity;
import bg.softuni.autho_moto_manager.repository.UserRepository;
import bg.softuni.autho_moto_manager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        UserEntity user = modelMapper.map(userRegisterDTO, UserEntity.class);
        userRepository.save(user);
    }
}
