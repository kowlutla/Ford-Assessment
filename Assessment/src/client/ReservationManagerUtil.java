package client;

import model.BusDetail;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ReservationManagerUtil {

    //Method to return month number by taking month name
    public static int getMonthNumberByName(String month) {
        //take first three characters of month
        month = month.substring(0, 3);
        //convert month name to lowercase
        month = month.toLowerCase();
        //get the month number based on month name
        switch (month) {
            case "jan":
                return 1;
            case "feb":
                return 2;
            case "mar":
                return 3;
            case "apr":
                return 4;
            case "may":
                return 5;
            case "jun":
                return 6;
            case "jul":
                return 7;
            case "aug":
                return 8;
            case "sep":
                return 9;
            case "oct":
                return 10;
            case "nov":
                return 11;
            case "dec":
                return 12;
        }
        return 0;
    }

    //Method to validate BusDetail Object by using custom annotations
    public static boolean isValid(BusDetail busDetails) {
        boolean isValid = true;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BusDetail>> constraintValidators = validator.validate(busDetails);
        if (constraintValidators.size() > 0) {
            System.out.println(constraintValidators.iterator().next().getMessage());
            isValid = false;
            // System.out.println(busDetails);
        } else {
            isValid = true;
        }
        return isValid;
    }
}
