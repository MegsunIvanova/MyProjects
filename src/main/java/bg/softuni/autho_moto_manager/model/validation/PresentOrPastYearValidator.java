package bg.softuni.autho_moto_manager.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class PresentOrPastYearValidator implements ConstraintValidator<PresentOrPastYear, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        return value == null ? true : (value <= Year.now().getValue() && value > 0);
    }
}
