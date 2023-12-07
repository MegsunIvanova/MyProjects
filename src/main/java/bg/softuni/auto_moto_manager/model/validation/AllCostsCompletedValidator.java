package bg.softuni.auto_moto_manager.model.validation;

import bg.softuni.auto_moto_manager.repository.CostRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllCostsCompletedValidator implements ConstraintValidator<AllCostsCompleted, String> {
    private final CostRepository costRepository;

    public AllCostsCompletedValidator(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return costRepository.countAllByVehicle_UuidAndCompleted(value, false) == 0;
    }
}
