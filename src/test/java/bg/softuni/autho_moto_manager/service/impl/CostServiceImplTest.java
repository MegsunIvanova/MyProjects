package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.view.CostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.DetailedCostsView;
import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import bg.softuni.autho_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.CurrencyRepository;
import bg.softuni.autho_moto_manager.service.CostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CostServiceImplTest {
    final BigDecimal EX_RATE1_COMPLETED = BigDecimal.valueOf(1.5000);
    final BigDecimal EX_RATE2_COMPLETED = BigDecimal.valueOf(1.4000);
    final BigDecimal EX_RATE_UNCOMPLETED = BigDecimal.valueOf(1.1000);
    final BigDecimal EX_RATE_CURRENCY = BigDecimal.valueOf(2.00000);
    final BigDecimal COST_AMOUNT = BigDecimal.valueOf(1000.00);
    private CostService serviceToTest;
    @Mock
    private CostRepository mockCostRepository;
    @Mock
    private CurrencyRepository mockCurrencyRepository;
    @Mock
    private ModelMapper mockModelMapper;

    String vehicleUUID;

    @BeforeEach
    void setUp() {
        serviceToTest = new CostServiceImpl(
                mockCostRepository,
                mockCurrencyRepository,
                mockModelMapper);

        vehicleUUID = UUID.randomUUID().toString();

        List<CostEntity> vehicleCosts = new ArrayList<>();
        vehicleCosts.addAll(costList(vehicleUUID, true, EX_RATE1_COMPLETED));
        vehicleCosts.addAll(costList(vehicleUUID, true, EX_RATE2_COMPLETED));
        vehicleCosts.addAll(costList(vehicleUUID, false, EX_RATE_UNCOMPLETED));
        vehicleCosts.addAll(costList(vehicleUUID, false, null));

        when(mockCostRepository.findAllByVehicle_Uuid(vehicleUUID))
                .thenReturn(vehicleCosts);
    }

    @Test
    void testGetDetailedCostsViewShouldMapCostsByTypeProperly() {
        DetailedCostsView detailedCostsView = serviceToTest.getDetailedCostsView(vehicleUUID);

        Map<CostTypeEnum, List<CostViewDTO>> costsByType = detailedCostsView.getCostsByType();

        assertEquals(CostTypeEnum.values().length, costsByType.keySet().size());
        for (CostTypeEnum key : costsByType.keySet()) {
            assertEquals(4, costsByType.get(key).size());
            assertTrue(costsByType.get(key).stream().allMatch(c -> c.getType().equals(key)));
        }
    }

    @Test
    void testGetDetailedCostsViewShouldMapCompletedCostsAmountProperly() {
        DetailedCostsView detailedCostsView = serviceToTest.getDetailedCostsView(vehicleUUID);

        Map<CostTypeEnum, BigDecimal> completedCostsAmount = detailedCostsView.getCompletedCostsAmount();
        BigDecimal cost1Amount = COST_AMOUNT.multiply(EX_RATE1_COMPLETED);
        BigDecimal cost2Amount = COST_AMOUNT.multiply(EX_RATE2_COMPLETED);
        BigDecimal sum = cost1Amount.add(cost2Amount);

        for (CostTypeEnum key : completedCostsAmount.keySet()) {
            assertEquals(sum.setScale(2, RoundingMode.HALF_UP), completedCostsAmount.get(key));
        }
    }

    @Test
    void testGetDetailedCostsViewShouldMapUncompletedCostsAmountProperly() {
        DetailedCostsView detailedCostsView = serviceToTest.getDetailedCostsView(vehicleUUID);

        Map<CostTypeEnum, BigDecimal> uncompletedCostsAmount = detailedCostsView.getUncompletedCostsAmount();
        BigDecimal unCost1Amount = COST_AMOUNT.multiply(EX_RATE_UNCOMPLETED);
        BigDecimal unCost2Amount = COST_AMOUNT.multiply(EX_RATE_CURRENCY);
        BigDecimal unSum = unCost1Amount.add(unCost2Amount);

        for (CostTypeEnum key : uncompletedCostsAmount.keySet()) {
            assertEquals(unSum.setScale(2, RoundingMode.HALF_UP), uncompletedCostsAmount.get(key));
        }

    }

    private List<CostEntity> costList(String vehicleUUID, boolean completed, BigDecimal transactionExRate) {
        return Arrays.stream(CostTypeEnum.values())
                .map(type -> mapToCostEntity(type, completed, transactionExRate))
                .collect(Collectors.toList());
    }

    private CostEntity mapToCostEntity(CostTypeEnum costType,
                                       boolean completed,
                                       BigDecimal transactionExRate) {
        return new CostEntity()
                .setType(costType)
                .setCompleted(completed)
                .setAmount(COST_AMOUNT)
                .setCurrency(new CurrencyEntity().setId("TEST").setRateToBGN(EX_RATE_CURRENCY))
                .setTransactionExRate(transactionExRate);
    }
}