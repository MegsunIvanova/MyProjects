package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.binding.UpdateCostDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.CostViewDTO;
import bg.softuni.autho_moto_manager.model.dto.view.DetailedCostsView;
import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import bg.softuni.autho_moto_manager.model.entity.CurrencyEntity;
import bg.softuni.autho_moto_manager.model.enums.CostTypeEnum;
import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.CurrencyRepository;
import bg.softuni.autho_moto_manager.repository.SaleRepository;
import bg.softuni.autho_moto_manager.service.CostService;
import bg.softuni.autho_moto_manager.service.exceptions.ObjectNotFoundException;
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
    private final SaleRepository saleRepository;

    public CostServiceImpl(CostRepository costRepository,
                           CurrencyRepository currencyRepository,
                           ModelMapper modelMapper, SaleRepository saleRepository) {
        this.costRepository = costRepository;
        this.currencyRepository = currencyRepository;
        this.modelMapper = modelMapper;
        this.saleRepository = saleRepository;
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

        boolean sold = saleRepository.findByVehicle_Uuid(vehicleUuid).isPresent();

        return new DetailedCostsView(completedCostsAmount, uncompletedCostsAmount, costsByType, sold);
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
                .setTransactionRate(costToUpdate.getTransactionRate())
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
                .setTransactionRate(updateCostDTO.getTransactionRate())
                .setCompleted(updateCostDTO.isCompleted());

        costRepository.save(updatedCost);
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
