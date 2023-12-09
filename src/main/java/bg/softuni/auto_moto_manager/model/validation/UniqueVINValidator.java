package bg.softuni.auto_moto_manager.model.validation;

import bg.softuni.auto_moto_manager.repository.VehicleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueVINValidator implements ConstraintValidator<UniqueVIN, String> {
    private final VehicleRepository vehicleRepository;

    public UniqueVINValidator(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return true;
        }

        return this.vehicleRepository.findByVin(value).isEmpty();
    }
}
