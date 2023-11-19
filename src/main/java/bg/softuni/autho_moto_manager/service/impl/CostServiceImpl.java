package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.CostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.DetailedCostsView;
import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.CurrencyRepository;
import bg.softuni.autho_moto_manager.service.CostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CostServiceImpl implements CostService {
    private final CostRepository costRepository;
    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;

    public CostServiceImpl(CostRepository costRepository,
                           CurrencyRepository currencyRepository,
                           ModelMapper modelMapper) {
        this.costRepository = costRepository;
        this.currencyRepository = currencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddCostViewDTO getAddCostViewDTO() {
        return new AddCostViewDTO(currencyRepository.findAllCurrencyIds());
    }

    @Override
    public void addCost(AddCostDTO addCostDTO) {
        CostEntity costEntity = modelMapper.map(addCostDTO, CostEntity.class);

        if (costEntity.isCompleted() && costEntity.getTransactionRate() == null) {
            BigDecimal fixRate = costEntity.getCurrency().getRateToBGN();
            costEntity.setTransactionRate(fixRate);
        }

        costRepository.save(costEntity);
    }

    @Override
    public DetailedCostsView getDetailedCostsView(String vehicleUuid) {

        Map<CostTypeEnum, List<CostViewDTO>> costsByType =
                createMapByType(costRepository.findAllByVehicle_Uuid(vehicleUuid));


        Map<CostTypeEnum, BigDecimal> completedCostsAmount = amountInBGNByCostType(costRepository
                .findAllByVehicle_UuidAndCompleted(vehicleUuid, true));


        Map<CostTypeEnum, BigDecimal> uncompletedCostsAmount = amountInBGNByCostType(costRepository
                .findAllByVehicle_UuidAndCompleted(vehicleUuid, false));

        return new DetailedCostsView(completedCostsAmount, uncompletedCostsAmount, costsByType);
    }

    private Map<CostTypeEnum, List<CostViewDTO>> createMapByType(List<CostEntity> costEntities) {
        return costEntities.stream()
                .map(CostViewDTO::new)
                .collect(groupingBy(
                        CostViewDTO::getType,
                        Collectors.toList()));
    }

    private Map<CostTypeEnum, BigDecimal> amountInBGNByCostType(List<CostEntity> costs) {
        return costs.stream().collect(Collectors.groupingBy(
                CostEntity::getType,
                Collectors.reducing(BigDecimal.ZERO,
                        CostEntity::getAmountInBGN,
                        BigDecimal::add)));
    }
}
