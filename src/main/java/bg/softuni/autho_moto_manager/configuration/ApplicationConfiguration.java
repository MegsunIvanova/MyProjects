package bg.softuni.autho_moto_manager.configuration;

import bg.softuni.autho_moto_manager.model.dto.binding.CreateModelDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.CreateVehicleDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.autho_moto_manager.model.entity.*;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.MakeRepository;
import bg.softuni.autho_moto_manager.repository.ModelRepository;
import bg.softuni.autho_moto_manager.repository.RoleRepository;
import bg.softuni.autho_moto_manager.service.exceptions.DatabaseException;
import bg.softuni.autho_moto_manager.service.exceptions.ObjectNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class ApplicationConfiguration {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;

    public ApplicationConfiguration(RoleRepository roleRepository, PasswordEncoder passwordEncoder, MakeRepository makeRepository, ModelRepository modelRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configMapUserRegistrationDToToUserEntity(modelMapper);
        configMapCreateModelDTOToModelEntity(modelMapper);
        configMapCreateVehicleDTOToVehicleEntity(modelMapper);

        return modelMapper;
    }

    private void configMapCreateVehicleDTOToVehicleEntity(ModelMapper modelMapper) {
        //CreateVehicleDTO -> VehicleEntity
        Converter<String, ModelEntity> modelConverter =
                ctx -> ctx.getSource() == null
                        ? null
                        : modelRepository.findByName(ctx.getSource())
                        .orElseThrow(() -> new ObjectNotFoundException("Model " + ctx.getSource() + " can not be found!"));

        modelMapper.createTypeMap(CreateVehicleDTO.class, VehicleEntity.class)
                .addMappings(mapper -> mapper
                        .using(modelConverter)
                        .map(CreateVehicleDTO::getModel, VehicleEntity::setModel));
    }

    private void configMapCreateModelDTOToModelEntity(ModelMapper modelMapper) {
        //CreateModelDTO -> ModelEntity
        Converter<String, MakeEntity> makerConverter =
                ctx -> ctx.getSource() == null
                        ? null
                        : makeRepository.findByName(ctx.getSource())
                        .orElse(makeRepository.save(new MakeEntity(ctx.getSource())));

        modelMapper.createTypeMap(CreateModelDTO.class, ModelEntity.class)
                .addMappings(mapper -> mapper
                        .using(makerConverter)
                        .map(CreateModelDTO::getMake, ModelEntity::setMake));
    }

    private void configMapUserRegistrationDToToUserEntity(ModelMapper modelMapper) {
        //UserRegistrationDTO -> UserEntity
        Provider<Set<RoleEntity>> roleProvider = p -> Set.of(roleRepository.findByRole(UserRoleEnum.USER)
                .orElseThrow(() -> new DatabaseException("User role 'USER' was not found in database!")));

        Converter<String, String> passwordConverter =
                ctx -> ctx.getSource() == null
                        ? null
                        : passwordEncoder.encode(ctx.getSource());

        modelMapper.createTypeMap(UserRegisterDTO.class, UserEntity.class)
                .addMappings(mapper -> mapper
                        .with(roleProvider)
                        .map(UserRegisterDTO::getRole, UserEntity::setRoles))
                .addMappings(mapper -> mapper
                        .using(passwordConverter)
                        .map(UserRegisterDTO::getPassword, UserEntity::setPassword)
                );
    }
}
