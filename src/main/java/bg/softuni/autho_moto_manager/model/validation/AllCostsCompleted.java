package bg.softuni.autho_moto_manager.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AllCostsCompletedValidator.class)
public @interface AllCostsCompleted {
    String message() default "All vehicle's costs must be completed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
