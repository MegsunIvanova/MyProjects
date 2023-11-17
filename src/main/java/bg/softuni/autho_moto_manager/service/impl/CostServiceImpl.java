package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.dto.binding.AddCostDTO;
import bg.softuni.autho_moto_manager.model.dto.view.AddCostViewDTO;
import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.CurrencyRepository;
import bg.softuni.autho_moto_manager.service.CostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
}
