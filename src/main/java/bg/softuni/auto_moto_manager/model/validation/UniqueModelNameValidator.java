package bg.softuni.auto_moto_manager.model.validation;

import bg.softuni.auto_moto_manager.repository.ModelRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueModelNameValidator implements ConstraintValidator<UniqueModelName, String> {
    private final ModelRepository modelRepository;

    public UniqueModelNameValidator(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.modelRepository.findByName(value).isEmpty();
    }
}
