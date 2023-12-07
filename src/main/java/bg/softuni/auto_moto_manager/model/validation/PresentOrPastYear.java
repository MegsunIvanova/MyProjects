package bg.softuni.auto_moto_manager.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PresentOrPastYearValidator.class)
public @interface PresentOrPastYear {
    String message() default "Year should be in present!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
