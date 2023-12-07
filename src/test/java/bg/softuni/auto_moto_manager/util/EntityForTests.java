package bg.softuni.auto_moto_manager.util;

import bg.softuni.auto_moto_manager.model.entity.*;
import bg.softuni.auto_moto_manager.model.enums.EngineEnum;
import bg.softuni.auto_moto_manager.model.enums.TransmissionEnum;
import bg.softuni.auto_moto_manager.model.enums.VehicleTypeEnum;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class EntityForTests {
    public static VehicleEntity createTestVehicle(UserEntity owner, ModelEntity model) {
        return new VehicleEntity()
                .setModel(model)
                .setYear(new Random().nextInt(1995, 2023))
                .setEngine(EngineEnum.ELECTRIC)
                .setTransmission(TransmissionEnum.AUTOMATIC)
                .setUuid(UUID.randomUUID().toString())
                .setOwner(owner);
    }

    public static ModelEntity createTestModel(MakeEntity make) {
        return new ModelEntity()
                .setMake(make)
                .setType(VehicleTypeEnum.AUTOMOBILE)
                .setName("TestModel");
    }

    public static UserEntity createTestUser(Set<RoleEntity> roles) {
        return new UserEntity().setName("Peter Petrov")
                .setEmail("peter@test.bg")
                .setRoles(roles)
                .setPassword("topsecret");
    }

    public static MakeEntity createMakeEntity() {
        return new MakeEntity("TestMake");
    }

    public static SaleEntity createTestSale (BigDecimal price,
                                             CurrencyEntity currency,
                                             BigDecimal transactionExRate,
                                             VehicleEntity vehicle) {
        return  new SaleEntity()
                .setPrice(price)
                .setCurrency(currency)
                .setTransactionExRate(transactionExRate)
                .setVehicle(vehicle)
                .setNotes("Test notes aboute sale");
    }

    public static CurrencyEntity createTestCurrency( BigDecimal transactionExRate) {
        return  new CurrencyEntity()
                .setId("TEST")
                .setRateToBGN(transactionExRate);
    }
}
