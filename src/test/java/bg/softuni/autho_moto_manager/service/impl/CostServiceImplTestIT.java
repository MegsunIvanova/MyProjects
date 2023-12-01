package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.UpdateCostDTO;
import bg.softuni.autho_moto_manager.model.entity.*;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.autho_moto_manager.model.enums.EngineEnum;
import bg.softuni.autho_moto_manager.model.enums.TransmissionEnum;
import bg.softuni.autho_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.autho_moto_manager.repository.*;
import bg.softuni.autho_moto_manager.service.CostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CostServiceImplTestIT {
    @Autowired
    CostService costServiceToTest;

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private SaleRepository saleRepository;

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
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        saleRepository.deleteAll();
        costRepository.deleteAll();
        currencyRepository.deleteAll();
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        saleRepository.deleteAll();
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

        VehicleEntity vehicle = createTestVehicle();
        CurrencyEntity currency = createTestCurrency("TEST");

        AddCostDTO addCostDTO = new AddCostDTO()
                .setVehicle(vehicle.getUuid())
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
        assertEquals(updateCostDTO.getAmount().setScale(2,RoundingMode.HALF_UP),
                optCost.get().getAmount());
        assertEquals(updateCostDTO.getCurrency(), optCost.get().getCurrency().getId());
        assertEquals(updateCostDTO.getTransactionExRate().setScale(5,RoundingMode.HALF_UP),
                optCost.get().getTransactionExRate());
        assertEquals(updateCostDTO.getDescription(), optCost.get().getDescription());

    }

    private static Stream<Arguments> testDataAddCost() {
        return Stream.of(
                Arguments.of(true, BigDecimal.valueOf(1000), BigDecimal.valueOf(2)),
                Arguments.of(true, BigDecimal.valueOf(1000), null),
                Arguments.of(false, BigDecimal.valueOf(1000), null)
        );
    }

    private MakeEntity createMakeEntity() {
        return makeRepository.save(new MakeEntity("TestMake"));
    }

    private ModelEntity createTestModel() {
        if (modelRepository.count() > 0) {
            return modelRepository.findByName("TestModel").orElseThrow();
        }

        return modelRepository.save(new ModelEntity()
                .setMake(createMakeEntity())
                .setType(VehicleTypeEnum.AUTOMOBILE)
                .setName("TestModel"));
    }

    private UserEntity createTestUser() {
        if (userRepository.count() > 0) {
            return userRepository.findByEmail("peter@test.bg").orElseThrow();
        }

            return userRepository.save(new UserEntity().setName("Peter Petrov")
                    .setEmail("peter@test.bg")
                    .setRoles(new HashSet<>(roleRepository.findAll()))
                    .setPassword("topsecret"));
    }

    private VehicleEntity createTestVehicle() {
        return vehicleRepository.save(new VehicleEntity()
                .setModel(createTestModel())
                .setYear(new Random().nextInt(1995, 2023))
                .setEngine(EngineEnum.ELECTRIC)
                .setTransmission(TransmissionEnum.AUTOMATIC)
                .setUuid(UUID.randomUUID().toString())
                .setOwner(createTestUser()));
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
                .setAmount(BigDecimal.valueOf(1000))
                .setCurrency(createTestCurrency("TEST"))
                .setTransactionExRate(BigDecimal.valueOf(2.00000))
                .setVehicle(createTestVehicle())
                .setCompleted(completed));
    }
}