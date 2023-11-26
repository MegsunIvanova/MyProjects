package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.UserEditDTO;
import bg.softuni.autho_moto_manager.model.entity.RoleEntity;
import bg.softuni.autho_moto_manager.model.entity.UserEntity;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.RoleRepository;
import bg.softuni.autho_moto_manager.repository.UserRepository;
import bg.softuni.autho_moto_manager.service.UserService;
import bg.softuni.autho_moto_manager.service.exceptions.DatabaseException;
import bg.softuni.autho_moto_manager.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        UserEntity user = modelMapper.map(userRegisterDTO, UserEntity.class);
        userRepository.save(user);
    }

    @Override
    public Optional<UserEditDTO> getUserForEdit(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapToUserEditDTO);
    }

    @Override
    @Transactional
    public void editRoles(UserEditDTO userEditDTO) {
        UserEntity userEntity = userRepository.findByEmail(userEditDTO.getEmail())
                .orElseThrow(() -> new ObjectNotFoundException("User with email " + userEditDTO.getEmail() + " was not found!"));

        RoleEntity adminRole = roleRepository
                .findByRole(UserRoleEnum.ADMIN)
                .orElseThrow(() -> new DatabaseException("User role 'ADMIN' was not found in database!"));

        if (userEditDTO.isAdmin()) {
            userEntity.addRole(adminRole);
        } else {
            userEntity.removeRole(adminRole);
        }

        userRepository.save(userEntity);
    }

    private UserEditDTO mapToUserEditDTO(UserEntity userEntity) {
        return new UserEditDTO()
                .setName(userEntity.getName())
                .setEmail(userEntity.getEmail())
                .setRoles(userEntity.getRoles()
                        .stream()
                        .map(RoleEntity::getRole)
                        .collect(Collectors.toSet()));
    }


}
