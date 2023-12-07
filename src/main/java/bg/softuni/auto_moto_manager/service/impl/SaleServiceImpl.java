package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.binding.SaleDTO;
import bg.softuni.auto_moto_manager.model.dto.view.SaleVehicleView;
import bg.softuni.auto_moto_manager.model.entity.CostEntity;
import bg.softuni.auto_moto_manager.model.entity.SaleEntity;
import bg.softuni.auto_moto_manager.model.entity.VehicleEntity;
import bg.softuni.auto_moto_manager.repository.CurrencyRepository;
import bg.softuni.auto_moto_manager.repository.SaleRepository;
import bg.softuni.auto_moto_manager.repository.VehicleRepository;
import bg.softuni.auto_moto_manager.service.SaleService;
import bg.softuni.auto_moto_manager.service.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository,
                           VehicleRepository vehicleRepository,
                           CurrencyRepository currencyRepository,
                           ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.vehicleRepository = vehicleRepository;
        this.currencyRepository = currencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public SaleVehicleView getSaleVehicleView(String uuid) {
        VehicleEntity vehicleEntity = vehicleRepository.getByUuid(uuid)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Vehicle with uuid " + uuid + " can not be found!"));

        boolean allCostsCompleted = vehicleEntity.getCostCalculation()
                .stream()
                .allMatch(CostEntity::isCompleted);

        List<String> currencyIds = currencyRepository.findAllCurrencyIds();

        return new SaleVehicleView()
                .setUuid(vehicleEntity.getUuid())
                .setTitle(vehicleEntity.getSummaryTitle())
                .setPrimaryImage(VehicleServiceImpl.primaryImgSrc(vehicleEntity))
                .setTotalCostsInBGN(vehicleEntity.getTotalCostsInBGN())
                .setAllCostsCompleted(allCostsCompleted)
                .setCurrenciesIds(currencyIds);
    }

    @Override
    public void sell(SaleDTO saleDTO) {
        SaleEntity saleEntity = modelMapper.map(saleDTO, SaleEntity.class);
        saleRepository.save(saleEntity);
    }

}
