package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Class that help BusNameAnnoation to validate Bus Number
public class BusNameValidation implements ConstraintValidator<BusNameAnnotation, String> {
    @Override
    public void initialize(BusNameAnnotation busNameAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //Pattern that matches first 3 digits followed by one Alphabet
        Pattern p = Pattern.compile("^[0-9]{3}[a-zA-z]");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
