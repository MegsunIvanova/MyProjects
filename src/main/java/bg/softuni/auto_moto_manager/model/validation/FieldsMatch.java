package bg.softuni.auto_moto_manager.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldsMatch {
    String first();

    String second();

    String message() default "Fields do not match.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
