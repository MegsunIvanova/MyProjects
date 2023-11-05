package bg.softuni.autho_moto_manager.configuration;

import bg.softuni.autho_moto_manager.model.dto.binding.UserRegisterDTO;
import bg.softuni.autho_moto_manager.model.entity.RoleEntity;
import bg.softuni.autho_moto_manager.model.entity.UserEntity;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.RoleRepository;
import bg.softuni.autho_moto_manager.service.exceptions.RoleNotFoundException;
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

    public ApplicationConfiguration(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //UserRegistrationDTO -> UserEntity
        Provider<Set<RoleEntity>> roleProvider = p -> Set.of(roleRepository.findByRole(UserRoleEnum.USER)
                .orElseThrow(() -> new RoleNotFoundException("User role 'USER' was not found!")));

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

        return modelMapper;
    }
}
