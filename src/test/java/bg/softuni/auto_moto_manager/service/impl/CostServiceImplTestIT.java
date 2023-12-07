package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.auto_moto_manager.model.dto.binding.UpdateCostDTO;
import bg.softuni.auto_moto_manager.model.entity.*;
import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.auto_moto_manager.repository.*;
import bg.softuni.auto_moto_manager.service.CostService;
import bg.softuni.auto_moto_manager.util.EntityForTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CostServiceImplTestIT {
    static final BigDecimal COST_AMOUNT = BigDecimal.valueOf(1000.00);

    @Autowired
    CostService costServiceToTest;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

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

    private UserEntity testUser;
    private VehicleEntity testVehicle;

    @BeforeEach
    void setUp() {
        costRepository.deleteAll();
        currencyRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        createEntities();
    }

    @AfterEach
    void tearDown() {
        costRepository.deleteAll();
        currencyRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @ParameterizedTest(name = "Test addCost completed {0}, amount {1}, transactionRate {2}")
    @MethodSource("testDataAddCost")
    void testAddCost(boolean completed,
                     BigDecimal amount,
                     BigDecimal transactionRate) {

        CurrencyEntity currency = createTestCurrency("TEST");

        AddCostDTO addCostDTO = new AddCostDTO()
                .setVehicle(testVehicle.getUuid())
                .setType(createTestCostType())
                .setCompleted(completed)
                .setAmount(amount)
                .setCurrency(currency.getId())
                .setTransactionExRate(transactionRate);

        costServiceToTest.addCost(addCostDTO);

        List<CostEntity> costs = costRepository.findAll();

        BigDecimal expTransactionRate = completed && addCostDTO.getTransactionExRate() == null
                ? currency.getRateToBGN()
                : addCostDTO.getTransactionExRate();

        if (expTransactionRate != null) {
            expTransactionRate = expTransactionRate.setScale(5, RoundingMode.HALF_UP);
        }

        assertEquals(1, costs.size());
        assertEquals(addCostDTO.getType(), costs.get(0).getType());
        assertEquals(addCostDTO.getAmount().setScale(2, RoundingMode.HALF_UP),
                costs.get(0).getAmount());
        assertEquals(addCostDTO.getCurrency(), costs.get(0).getCurrency().getId());
        assertEquals(expTransactionRate, costs.get(0).getTransactionExRate());
        assertEquals(addCostDTO.isCompleted(), costs.get(0).isCompleted());
        assertEquals(addCostDTO.getVehicle(), costs.get(0).getVehicle().getUuid());
    }

    @Test
    void testDeleteCost() {
        CostEntity testCost1 = createTestCost(true);
        CostEntity testCost2 = createTestCost(false);

        assertEquals(2, costRepository.count());

        costServiceToTest.delete(testCost1.getId());
        assertEquals(1, costRepository.count());
        assertTrue(costRepository.findById(testCost1.getId()).isEmpty());

        costServiceToTest.delete(testCost2.getId());
        assertEquals(0, costRepository.count());
        assertTrue(costRepository.findById(testCost2.getId()).isEmpty());
    }

    @Test
    void testUpdateCost() {
        CostEntity testCost = createTestCost(false);

        UpdateCostDTO updateCostDTO = new UpdateCostDTO()
                .setId(testCost.getId())
                .setVehicle(testCost.getVehicle().getUuid())
                .setType(createTestCostType())
                .setCompleted(true)
                .setAmount(testCost.getAmount().add(BigDecimal.valueOf(500)))
                .setCurrency(createTestCurrency("UPDATED").getId())
                .setTransactionExRate(BigDecimal.valueOf(3.00000))
                .setDescription("Test description from update!");

        costServiceToTest.updateCost(updateCostDTO);

        Optional<CostEntity> optCost = costRepository.findById(testCost.getId());

        assertTrue(optCost.isPresent());
        assertEquals(updateCostDTO.getType(), optCost.get().getType());
        assertEquals(updateCostDTO.isCompleted(), optCost.get().isCompleted());
        assertEquals(updateCostDTO.getAmount().setScale(2, RoundingMode.HALF_UP),
                optCost.get().getAmount());
        assertEquals(updateCostDTO.getCurrency(), optCost.get().getCurrency().getId());
        assertEquals(updateCostDTO.getTransactionExRate().setScale(5, RoundingMode.HALF_UP),
                optCost.get().getTransactionExRate());
        assertEquals(updateCostDTO.getDescription(), optCost.get().getDescription());

    }

    private static Stream<Arguments> testDataAddCost() {
        return Stream.of(
                Arguments.of(true, COST_AMOUNT, BigDecimal.valueOf(2)),
                Arguments.of(true, COST_AMOUNT, null),
                Arguments.of(false, COST_AMOUNT, null)
        );
    }

    private CostTypeEnum createTestCostType() {
        CostTypeEnum[] costTypes = CostTypeEnum.values();
        int i = new Random().nextInt(0, costTypes.length);
        return costTypes[i];
    }

    private CurrencyEntity createTestCurrency(String id) {
        CurrencyEntity currencyEntity = new CurrencyEntity()
                .setId(id)
                .setRateToBGN(BigDecimal.valueOf(new Random().nextDouble(0.10000, 2.00000)));

        return currencyRepository.save(currencyEntity);
    }

    private CostEntity createTestCost(boolean completed) {
        return costRepository.save(new CostEntity()
                .setType(createTestCostType())
                .setAmount(COST_AMOUNT)
                .setCurrency(createTestCurrency("TEST"))
                .setTransactionExRate(BigDecimal.valueOf(2.00000))
                .setVehicle(testVehicle)
                .setCompleted(completed));
    }

    private void createEntities() {
        testUser = userRepository.save(EntityForTests.createTestUser(new HashSet<>(roleRepository.findAll())));

        MakeEntity testMake = makeRepository.save(EntityForTests.createMakeEntity());

        ModelEntity testModel = modelRepository.save(EntityForTests.createTestModel(testMake));

        testVehicle = vehicleRepository.save(
                EntityForTests.createTestVehicle(testUser, testModel));
    }

}