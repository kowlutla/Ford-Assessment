package exceptions;

//Custom Exception BusNotFoundException
public class BusNotFoundException extends RuntimeException {

    public BusNotFoundException(String msg) {
        super(msg);
    }
}
