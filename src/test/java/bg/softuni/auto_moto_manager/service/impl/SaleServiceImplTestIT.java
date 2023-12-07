package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.view.SaleVehicleView;
import bg.softuni.auto_moto_manager.model.entity.*;
import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.auto_moto_manager.repository.*;
import bg.softuni.auto_moto_manager.service.SaleService;
import bg.softuni.auto_moto_manager.util.EntityForTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@SpringBootTest
class SaleServiceImplTestIT {
    private final BigDecimal COST_AMOUNT = BigDecimal.valueOf(1000.00);
    final BigDecimal EX_RATE_SALE = BigDecimal.valueOf(2.20000);
    final BigDecimal EX_RATE_COST = BigDecimal.valueOf(1.80000);
    final BigDecimal EX_RATE_CURRENCY = BigDecimal.valueOf(2.00000);

    @Autowired
    private SaleService saleServiceToTest;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CostRepository costRepository;

    private VehicleEntity testVehicle;
    CurrencyEntity testCurrency;

    @BeforeEach
    void setUp() {
        costRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        currencyRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();

        createEntities();
    }


    @AfterEach
    void tearDown() {
        costRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        currencyRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();
    }

    @Test
    void testGetSaleVehicleViewShouldMapProperlyWithUncompletedCost() {
        CostEntity cost1 = costRepository.save(costEntityToTest(true, EX_RATE_COST, testCurrency, testVehicle));
        CostEntity cost2 = costRepository.save(costEntityToTest(false, EX_RATE_COST, testCurrency, testVehicle));

        testVehicle.setCostCalculation(Set.of(cost1, cost2));
        vehicleRepository.save(testVehicle);

        SaleVehicleView saleVehicleView1 =
                saleServiceToTest.getSaleVehicleView(testVehicle.getUuid());

        Assertions.assertEquals(testVehicle.getUuid(), saleVehicleView1.getUuid());
        Assertions.assertEquals(testVehicle.getSummaryTitle(), saleVehicleView1.getTitle());

        BigDecimal expectedTotalCostsInBGN = COST_AMOUNT.multiply(EX_RATE_COST).multiply(BigDecimal.valueOf(2));
        Assertions.assertEquals(expectedTotalCostsInBGN.setScale(2, RoundingMode.HALF_UP),
                saleVehicleView1.getTotalCostsInBGN());
        Assertions.assertFalse(saleVehicleView1.isAllCostsCompleted());
    }

    @Test
    void testGetSaleVehicleViewShouldMapProperlyAllCostsCompleted() {
        CostEntity cost1 = costRepository.save(costEntityToTest(true, EX_RATE_COST, testCurrency, testVehicle));
        CostEntity cost2 = costRepository.save(costEntityToTest(true, EX_RATE_COST, testCurrency, testVehicle));

        testVehicle.setCostCalculation(Set.of(cost1, cost2));
        vehicleRepository.save(testVehicle);

        SaleVehicleView saleVehicleView1 =
                saleServiceToTest.getSaleVehicleView(testVehicle.getUuid());

        Assertions.assertEquals(testVehicle.getUuid(), saleVehicleView1.getUuid());
        Assertions.assertEquals(testVehicle.getSummaryTitle(), saleVehicleView1.getTitle());

        BigDecimal expectedTotalCostsInBGN = COST_AMOUNT.multiply(EX_RATE_COST).multiply(BigDecimal.valueOf(2));
        Assertions.assertEquals(expectedTotalCostsInBGN.setScale(2, RoundingMode.HALF_UP),
                saleVehicleView1.getTotalCostsInBGN());
        Assertions.assertTrue(saleVehicleView1.isAllCostsCompleted());
    }

    private void createEntities() {
        testCurrency = currencyRepository.save(EntityForTests.createTestCurrency(EX_RATE_CURRENCY));

        UserEntity owner = userRepository.save(
                EntityForTests.createTestUser(new HashSet<>(roleRepository.findAll())));
        MakeEntity testMake = makeRepository.save(EntityForTests.createMakeEntity());
        ModelEntity testModel = modelRepository.save(EntityForTests.createTestModel(testMake));

        testVehicle = vehicleRepository.save(
                EntityForTests.createTestVehicle(owner, testModel));

    }

    private CostEntity costEntityToTest(boolean completed,
                                        BigDecimal transactionExRate,
                                        CurrencyEntity currency,
                                        VehicleEntity vehicle) {
        CostTypeEnum[] types = CostTypeEnum.values();
        CostTypeEnum type = types[new Random().nextInt(0, types.length)];

        return new CostEntity()
                .setType(type)
                .setAmount(COST_AMOUNT)
                .setCurrency(currency)
                .setCompleted(completed)
                .setTransactionExRate(transactionExRate)
                .setVehicle(vehicle);
    }


}