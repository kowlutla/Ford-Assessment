package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//Class that help SeatNumbersAnnotations to validate Seat Numbers
public class SeatNumberValidation implements ConstraintValidator<SeatNumbersAnnotation, Integer> {

    @Override
    public void initialize(SeatNumbersAnnotation seatNumbersAnnotation) {
    }

    @Override
    public boolean isValid(Integer seatCount, ConstraintValidatorContext constraintValidatorContext) {
        return seatCount >= 0;
    }
}
