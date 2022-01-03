package validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Annotation for Validation Of Seats Count
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SeatNumberValidation.class)
public @interface SeatNumbersAnnotation {
    String message() default "";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}
