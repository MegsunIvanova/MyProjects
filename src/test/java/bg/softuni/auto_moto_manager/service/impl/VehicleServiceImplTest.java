package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.view.VehicleDetailsViewDTO;
import bg.softuni.auto_moto_manager.model.entity.*;
import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.auto_moto_manager.model.enums.EngineEnum;
import bg.softuni.auto_moto_manager.model.enums.TransmissionEnum;
import bg.softuni.auto_moto_manager.model.enums.VehicleTypeEnum;
import bg.softuni.auto_moto_manager.repository.MakeRepository;
import bg.softuni.auto_moto_manager.repository.VehicleRepository;
import bg.softuni.auto_moto_manager.service.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {
    final BigDecimal EX_RATE_COMPLETED = BigDecimal.valueOf(1.5000);
    final BigDecimal EX_RATE_UNCOMPLETED = BigDecimal.valueOf(1.1000);
    final BigDecimal EX_RATE_CURRENCY = BigDecimal.valueOf(2.00000);
    final BigDecimal COST_AMOUNT = BigDecimal.valueOf(1000.00);
    private VehicleService serviceToTest;
    private VehicleEntity testVehicle;
    private VehicleDetailsViewDTO vehicleDetailsViewDTO;
    private CurrencyEntity currency;

    @Mock
    private MakeRepository mockMakeRepository;
    @Mock
    private VehicleRepository mockVehicleRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new VehicleServiceImpl(
                mockMakeRepository,
                mockVehicleRepository,
                mockModelMapper);


        currency = new CurrencyEntity()
                .setId("TEST")
                .setRateToBGN(EX_RATE_CURRENCY);

        testVehicle = createTestVehicle(UUID.randomUUID().toString());

        when(mockVehicleRepository.findByUuid(testVehicle.getUuid()))
                .thenReturn(Optional.of(testVehicle));

        vehicleDetailsViewDTO = serviceToTest
                .getDetailsByUuid(testVehicle.getUuid());
    }

    @Test
    void testGetDetailsByUuidWillMapNotesProperly() {
        String expectedNotes = testVehicle.getNotes();
        String actualNotes = vehicleDetailsViewDTO.getNotes();

        Assertions.assertEquals(expectedNotes, actualNotes);
    }

    @Test
    void testGetDetailsByUuidWillMapPicturesProperly() {
        int expectedSize = testVehicle.getPictures().size();
        int actualSize = vehicleDetailsViewDTO.getPictures().size();
        Assertions.assertEquals(expectedSize, actualSize);

        for (PictureEntity expectedPicture : testVehicle.getPictures()) {
            Assertions.assertTrue(vehicleDetailsViewDTO.getPictures()
                    .stream()
                    .anyMatch(actualPicture -> actualPicture.getUrl().equals(expectedPicture.getUrl())));
        }
    }

    @Test
    void testGetDetailsByUuidWillMapCostsProperly() {
        CostTypeEnum[] costType = CostTypeEnum.values();
        int expectedCount = costType.length;
        int actualCount = vehicleDetailsViewDTO.getTotalCostsByType().values().size();
        Assertions.assertEquals(expectedCount, actualCount);

        BigDecimal completedTotalCost = COST_AMOUNT.multiply(EX_RATE_COMPLETED);
        BigDecimal uncompletedTotalCost1 = COST_AMOUNT.multiply(EX_RATE_CURRENCY);
        BigDecimal uncompletedTotalCost2 = COST_AMOUNT.multiply(EX_RATE_UNCOMPLETED);

        BigDecimal expectedTotalCostByType = completedTotalCost
                .add(uncompletedTotalCost1)
                .add(uncompletedTotalCost2);

        for (CostTypeEnum type : costType) {
            BigDecimal actualTotalCostByType = vehicleDetailsViewDTO.getTotalCostsByType().get(type);
            Assertions.assertEquals(expectedTotalCostByType, actualTotalCostByType);
        }

        BigDecimal expectedTotalCosts = expectedTotalCostByType.multiply(BigDecimal.valueOf(costType.length));

        Assertions.assertEquals(expectedTotalCosts, vehicleDetailsViewDTO.getTotalCostsInBGN());
    }

    private VehicleEntity createTestVehicle(String vehicleUUID) {
        Set<PictureEntity> testPictures = createTestPictures();

        return new VehicleEntity()
                .setUuid(vehicleUUID)
                .setEngine(EngineEnum.GAS)
                .setModel(new ModelEntity()
                        .setMake(new MakeEntity("TEST MAKE"))
                        .setName("TEST MODEL")
                        .setType(VehicleTypeEnum.AUTOMOBILE))
                .setOwner(new UserEntity())
                .setOdometerInKm(10000)
                .setTransmission(TransmissionEnum.MANUAL)
                .setYear(2000)
                .setVin("VIN for test")
                .setNotes("Notes for test")
                .setPictures(testPictures)
                .setPrimaryImage((testPictures.stream().findFirst().orElseThrow()))
                .setCostCalculation(createTestCosts());
    }

    private Set<PictureEntity> createTestPictures() {
        Set<PictureEntity> pictures = new HashSet<>();
        for (int i = 1; i <= 5; i++) {
            PictureEntity picture = new PictureEntity().setUrl("Test url " + i);
            picture.setId((long) i);
            pictures.add(picture);
        }

        return pictures;
    }

    private Set<CostEntity> createTestCosts() {
        Set<CostEntity> costs = new HashSet<>();

        long id = 1L;

        for (CostTypeEnum type : CostTypeEnum.values()) {
            CostEntity testCostCompleted = createTestCost(
                    type,
                    true,
                    EX_RATE_COMPLETED,
                    COST_AMOUNT,
                    id++);

            CostEntity testCostUncompleted = createTestCost(
                    type,
                    false,
                    null,
                    COST_AMOUNT,
                    id++);

            CostEntity testCostUncompletedWithRate = createTestCost(
                    type,
                    false,
                    EX_RATE_UNCOMPLETED,
                    COST_AMOUNT,
                    id++);

            costs.add(testCostCompleted);
            costs.add(testCostUncompleted);
            costs.add(testCostUncompletedWithRate);
        }

        return costs;
    }

    private CostEntity createTestCost(CostTypeEnum type,
                                      boolean completed,
                                      BigDecimal transactionExRate,
                                      BigDecimal amount,
                                      Long id) {

        CostEntity costEntity = new CostEntity()
                .setType(type)
                .setCurrency(currency)
                .setCompleted(completed)
                .setTransactionExRate(transactionExRate)
                .setAmount(amount);
        costEntity.setId(id);

        return costEntity;
    }

}