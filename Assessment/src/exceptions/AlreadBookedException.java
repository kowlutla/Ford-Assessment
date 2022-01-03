package exceptions;

//Custom exception that will throw when Passenger already booked ticket
public class AlreadBookedException extends RuntimeException {

    public AlreadBookedException(String msg) {
        super(msg);
    }
}
