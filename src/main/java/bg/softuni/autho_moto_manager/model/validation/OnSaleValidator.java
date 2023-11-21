package bg.softuni.autho_moto_manager.model.validation;

import bg.softuni.autho_moto_manager.repository.SaleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OnSaleValidator implements ConstraintValidator<OnSale, String> {
    private final SaleRepository saleRepository;

    public OnSaleValidator(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return saleRepository.findByVehicle_Uuid(value).isEmpty();
    }
}
