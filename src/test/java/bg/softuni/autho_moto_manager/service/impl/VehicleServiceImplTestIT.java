package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.view.SaleViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.autho_moto_manager.model.entity.*;
import bg.softuni.autho_moto_manager.repository.*;
import bg.softuni.autho_moto_manager.service.VehicleService;
import bg.softuni.autho_moto_manager.util.EntityForTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class VehicleServiceImplTestIT {
    final BigDecimal EX_RATE_SALE = BigDecimal.valueOf(2.20000);
    final BigDecimal EX_RATE_CURRENCY = BigDecimal.valueOf(2.00000);
    final BigDecimal SALE_PRICE = BigDecimal.valueOf(1100.00);

    @Autowired
    private VehicleService vehicleServiceToTest;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SaleRepository saleRepository;
    private ModelMapper modelMapper;

    private VehicleEntity testVehicle;
    private CurrencyEntity testCurrency;

    @BeforeEach
    void setUp() {
        saleRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        currencyRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();

        createEntities();
    }

    @AfterEach
    void tearDown() {
        saleRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        currencyRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();
    }

    @Test
    void testGetDetailsByUuidWillMapSaleProperly() {
        SaleEntity testSale = saleRepository.save(
                EntityForTests.createTestSale(SALE_PRICE, testCurrency, EX_RATE_SALE, testVehicle));

        vehicleRepository.save(testVehicle.setSale(testSale));

        VehicleDetailsViewDTO vehicleDetailsViewDTO =
                vehicleServiceToTest.getDetailsByUuid(testVehicle.getUuid());

        SaleEntity expectedSale = testVehicle.getSale();
        SaleViewDTO actualSale = vehicleDetailsViewDTO.getSale();

        Assertions.assertEquals(expectedSale.getPrice().setScale(2, RoundingMode.HALF_UP), actualSale.getPrice());
        Assertions.assertEquals(expectedSale.getCurrency().getId(), actualSale.getCurrencyId());
        Assertions.assertEquals(expectedSale.getTransactionExRate().setScale(5, RoundingMode.HALF_UP), actualSale.getTransactionExRate());
        Assertions.assertEquals(expectedSale.getNotes(), actualSale.getNotes());

        BigDecimal expectedSalePriceInBGN = expectedSale.getPrice().multiply(expectedSale.getTransactionExRate());
        Assertions.assertEquals(expectedSalePriceInBGN, actualSale.getSalePriceInBGN());
    }

    private void createEntities() {
        List<RoleEntity> roles = roleRepository.findAll();

        UserEntity testUser = userRepository.save(
                EntityForTests.createTestUser(new HashSet<>(roles)));

        MakeEntity testMake = makeRepository.save(EntityForTests.createMakeEntity());

        ModelEntity testModel = modelRepository.save(
                EntityForTests.createTestModel(testMake));

        testVehicle = vehicleRepository.save(
                EntityForTests.createTestVehicle(testUser, testModel));

        testCurrency = currencyRepository.save(
                EntityForTests.createTestCurrency(EX_RATE_CURRENCY));
    }
}

