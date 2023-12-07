package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.auto_moto_manager.model.dto.binding.UpdateCostDTO;
import bg.softuni.auto_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.auto_moto_manager.model.dto.view.CostViewDTO;
import bg.softuni.auto_moto_manager.model.dto.view.DetailedCostsView;
import bg.softuni.auto_moto_manager.model.entity.CostEntity;
import bg.softuni.auto_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.auto_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.auto_moto_manager.repository.CostRepository;
import bg.softuni.auto_moto_manager.repository.CurrencyRepository;
import bg.softuni.auto_moto_manager.service.CostService;
import bg.softuni.auto_moto_manager.service.aop.WarnIfExecutionExceeds;
import bg.softuni.auto_moto_manager.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (costEntity.isCompleted() && costEntity.getTransactionExRate() == null) {
            BigDecimal fixRate = costEntity.getCurrency().getRateToBGN();
            costEntity.setTransactionExRate(fixRate);
        }

        costRepository.save(costEntity);
    }

    @WarnIfExecutionExceeds(timeInMillis = 1000)
    @Override
    public DetailedCostsView getDetailedCostsView(String vehicleUuid) {
        List<CostEntity> costsByVehicleUuid =
                costRepository.findAllByVehicle_Uuid(vehicleUuid);

        Map<CostTypeEnum, List<CostViewDTO>> costsByType = new HashMap<>();
        Map<CostTypeEnum, BigDecimal> completedCostsAmount = new HashMap<>();
        Map<CostTypeEnum, BigDecimal> uncompletedCostsAmount = new HashMap<>();

        fillCostMaps(costsByVehicleUuid, costsByType, completedCostsAmount, uncompletedCostsAmount);

        return new DetailedCostsView(completedCostsAmount, uncompletedCostsAmount, costsByType);
    }

    @Override
    public void delete(Long id) {
        costRepository.deleteById(id);
    }

    @Override
    public UpdateCostDTO getUpdateCostDTO(Long costId, String vehicleUuid) {
        CostEntity costToUpdate = costRepository.findByIdAndVehicle_Uuid(costId, vehicleUuid)
                .orElseThrow(() -> new ObjectNotFoundException("Cost with id: " + costId +
                        " and vehicle uuid: " + vehicleUuid + " was not found."));

        return new UpdateCostDTO()
                .setId(costToUpdate.getId())
                .setType(costToUpdate.getType())
                .setDescription(costToUpdate.getDescription())
                .setAmount(costToUpdate.getAmount())
                .setCurrency(costToUpdate.getCurrency().getId())
                .setTransactionExRate(costToUpdate.getTransactionExRate())
                .setCompleted(costToUpdate.isCompleted())
                .setVehicle(costToUpdate.getVehicle().getUuid());
    }

    @Override
    public void updateCost(UpdateCostDTO updateCostDTO) {
        CostEntity costToUpdate = costRepository
                .findByIdAndVehicle_Uuid(updateCostDTO.getId(), updateCostDTO.getVehicle())
                .orElseThrow(() -> new ObjectNotFoundException("Cost with id: " + updateCostDTO.getId() +
                        " and vehicle uuid: " + updateCostDTO.getVehicle() + " was not found."));

        CurrencyEntity currencyEntity = currencyRepository.findById(updateCostDTO.getCurrency())
                .orElseThrow(() -> new ObjectNotFoundException("Currency " + updateCostDTO.getCurrency() +
                        " was not found."));

        CostEntity updatedCost = costToUpdate.setType(updateCostDTO.getType())
                .setDescription(updateCostDTO.getDescription())
                .setAmount(updateCostDTO.getAmount())
                .setCurrency(currencyEntity)
                .setTransactionExRate(updateCostDTO.getTransactionExRate())
                .setCompleted(updateCostDTO.isCompleted());

        costRepository.save(updatedCost);
    }

    private void fillCostMaps(List<CostEntity> costsByVehicleUuid,
                              Map<CostTypeEnum, List<CostViewDTO>> costsByType,
                              Map<CostTypeEnum, BigDecimal> completedCostsAmount,
                              Map<CostTypeEnum, BigDecimal> uncompletedCostsAmount) {
        for (CostEntity cost : costsByVehicleUuid) {
            CostViewDTO costViewDTO = new CostViewDTO(cost);

            CostTypeEnum type = costViewDTO.getType();

            costsByType.putIfAbsent(type, new ArrayList<>());
            costsByType.get(type).add(costViewDTO);

            if (costViewDTO.isCompleted()) {
                completedCostsAmount.putIfAbsent(type, BigDecimal.ZERO);
                BigDecimal sum = completedCostsAmount.get(type)
                        .add(costViewDTO.getAmountInBGN());
                completedCostsAmount.put(type, sum);
            } else {
                uncompletedCostsAmount.putIfAbsent(type, BigDecimal.ZERO);
                BigDecimal sum = uncompletedCostsAmount.get(type)
                        .add(costViewDTO.getAmountInBGN());
                uncompletedCostsAmount.put(type, sum);
            }
        }
    }
}
