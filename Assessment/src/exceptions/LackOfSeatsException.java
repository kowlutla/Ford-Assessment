package exceptions;

//Custom Exception LackOfSeatsException
public class LackOfSeatsException extends RuntimeException {

    public LackOfSeatsException(String msg) {
        super(msg);
    }
}
