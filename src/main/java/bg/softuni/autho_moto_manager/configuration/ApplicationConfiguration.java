package bg.softuni.autho_moto_manager.configuration;

import bg.softuni.autho_moto_manager.model.dto.binding.*;
import bg.softuni.autho_moto_manager.model.entity.*;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.*;
import bg.softuni.autho_moto_manager.service.exceptions.DatabaseException;
import bg.softuni.autho_moto_manager.service.exceptions.ObjectNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Configuration
public class ApplicationConfiguration {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrencyRepository currencyRepository;

    public ApplicationConfiguration(RoleRepository roleRepository,
                                    PasswordEncoder passwordEncoder,
                                    MakeRepository makeRepository,
                                    ModelRepository modelRepository,
                                    VehicleRepository vehicleRepository, CurrencyRepository currencyRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.vehicleRepository = vehicleRepository;
        this.currencyRepository = currencyRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        configMapUserRegistrationDToToUserEntity(modelMapper);
        configMapCreateModelDTOToModelEntity(modelMapper);
        configMapCreateVehicleDTOToVehicleEntity(modelMapper);
        configMapAddPictureDTOToPictureEntity(modelMapper);
        configMapAddCostDTOToCostEntity(modelMapper);
        configMapSaleDTOToSaleEntity(modelMapper);

        return modelMapper;
    }

    private void configMapSaleDTOToSaleEntity(ModelMapper modelMapper) {
        //SaleDTO -> SaleEntity
        modelMapper.createTypeMap(SaleDTO.class, SaleEntity.class)
                .addMappings(mapper -> mapper.skip(SaleEntity::setVehicle))
                .addMappings(mapper -> mapper
                        .using((getStringVehicleEntityConverter()))
                        .map(SaleDTO::getVehicle, SaleEntity::setVehicle))
                .addMappings(mapping -> mapping
                        .using(getStringCurrencyEntityConverter())
                        .map(SaleDTO::getCurrency, SaleEntity::setCurrency));

    }

    private void configMapAddCostDTOToCostEntity(ModelMapper modelMapper) {
        //AddCostDTO -> CostEntity
        modelMapper.createTypeMap(AddCostDTO.class, CostEntity.class)
                .addMappings(mapper -> mapper.skip(CostEntity::setVehicle))
                .addMappings(mapper -> mapper
                        .using((getStringVehicleEntityConverter()))
                        .map(AddCostDTO::getVehicle, CostEntity::setVehicle))
                .addMappings(mapping -> mapping
                        .using(getStringCurrencyEntityConverter())
                        .map(AddCostDTO::getCurrency, CostEntity::setCurrency));
    }

    private void configMapAddPictureDTOToPictureEntity(ModelMapper modelMapper) {
        //AddPictureDTO -> PictureEntity
//        Converter<BigDecimal, BigDecimal> rateConverter =
     //TODO

        modelMapper.createTypeMap(AddPictureDTO.class, PictureEntity.class)
                .addMappings(mapper -> mapper.skip(PictureEntity::setVehicle))
                .addMappings((mapper -> mapper
                        .using(getStringVehicleEntityConverter())
                        .map(AddPictureDTO::getVehicle, PictureEntity::setVehicle)));
    }

    private Converter<String, VehicleEntity> getStringVehicleEntityConverter() {
        return ctx -> ctx.getSource() == null
                ? null
                : vehicleRepository.findByUuid(ctx.getSource())
                .orElseThrow(() -> new ObjectNotFoundException("Vehicle with id:" + ctx.getSource() + "was not found!"));
    }

    private Converter<String, CurrencyEntity> getStringCurrencyEntityConverter() {
        return ctx -> ctx.getSource() == null
                ? null
                : currencyRepository.findById(ctx.getSource())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Currency " + ctx.getSource() + " was not found!"));
    }

    private void configMapCreateVehicleDTOToVehicleEntity(ModelMapper modelMapper) {
        //CreateVehicleDTO -> VehicleEntity
        Converter<String, ModelEntity> modelConverter =
                ctx -> ctx.getSource() == null
                        ? null
                        : modelRepository.findByName(ctx.getSource())
                        .orElseThrow(() -> new ObjectNotFoundException("Model " + ctx.getSource() + " was not found!"));

        Provider<String> uuidProvider = p -> String.valueOf(UUID.randomUUID());

        modelMapper.createTypeMap(CreateVehicleDTO.class, VehicleEntity.class)
                .addMappings(mapper -> mapper
                        .using(modelConverter)
                        .map(CreateVehicleDTO::getModel, VehicleEntity::setModel))
                .addMappings(mapper -> mapper
                        .with(uuidProvider)
                        .map(CreateVehicleDTO::getUuid, VehicleEntity::setUuid));
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
