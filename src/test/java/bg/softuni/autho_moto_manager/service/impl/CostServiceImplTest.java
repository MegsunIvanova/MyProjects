package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.repository.CostRepository;
import bg.softuni.autho_moto_manager.repository.CurrencyRepository;
import bg.softuni.autho_moto_manager.repository.SaleRepository;
import bg.softuni.autho_moto_manager.service.CostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CostServiceImplTest {

    private CostService serviceToTest;

    @Mock
    private CurrencyRepository mockCurrencyRepository;
    @Mock
    private CostRepository mockCostRepository;
    private SaleRepository mockSaleRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        serviceToTest = new CostServiceImpl(
                mockCostRepository,
                mockCurrencyRepository,
                mockSaleRepository,
                modelMapper
        );
    }

    @Test
    void testGetAddCostViewDTO() {

    }


}