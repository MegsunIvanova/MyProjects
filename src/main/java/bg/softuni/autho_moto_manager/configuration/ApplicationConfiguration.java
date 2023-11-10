package bg.softuni.autho_moto_manager.configuration;

import bg.softuni.autho_moto_manager.model.dto.binding.CreateModelDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.autho_moto_manager.model.entity.MakerEntity;
import bg.softuni.autho_moto_manager.model.entity.ModelEntity;
import bg.softuni.autho_moto_manager.model.entity.RoleEntity;
import bg.softuni.autho_moto_manager.model.entity.UserEntity;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.MakerRepository;
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
    private final MakerRepository makerRepository;

    public ApplicationConfiguration(RoleRepository roleRepository, PasswordEncoder passwordEncoder, MakerRepository makerRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.makerRepository = makerRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

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

        //CreateModelDTO -> ModelEntity
        Converter<String, MakerEntity> makerConverter =
                ctx -> ctx.getSource() == null
                        ? null
                        : makerRepository.findByName(ctx.getSource())
                        .orElse(makerRepository.save(new MakerEntity(ctx.getSource())));
        modelMapper.createTypeMap(CreateModelDTO.class, ModelEntity.class)
                .addMappings(mapper -> mapper
                        .using(makerConverter)
                        .map(CreateModelDTO::getMaker, ModelEntity::setMaker));

        return modelMapper;
    }
}
