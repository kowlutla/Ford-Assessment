package client;

import dao.BusDetailsDAO;
import dao.TablesDAO;
import exceptions.BusNotFoundException;
import exceptions.LackOfSeatsException;
import model.BusMaster;
import model.PassengerInfo;
import model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {
    ReservationManager manager;
    List<PassengerInfo> passengers;
    BusDetailsDAO busDetailsDAO;

    @BeforeEach
    void setUp() {

        busDetailsDAO = new BusDetailsDAO();
        manager = new ReservationManager();
        manager.loadBusMasterList();

        passengers = new ArrayList<>();
        passengers.add(new PassengerInfo(151337, "Kowlutla", 'F', 21, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), false, false));
        passengers.add(new PassengerInfo(151129, "Sudeepthi", 'F', 20, "Chennai", "Madurai", java.sql.Date.valueOf("2022-08-02"), false, true));
        passengers.add(new PassengerInfo(151330, "Hari Krishna", 'M', 25, "Chennai", "Bangalore", java.sql.Date.valueOf("2022-08-02"), false, false));
        passengers.add(new PassengerInfo(151331, "Deekshita", 'F', 18, "Chennai", "Bangalore", java.sql.Date.valueOf("2022-08-01"), false, true));
        passengers.add(new PassengerInfo(151338, "Roopa", 'F', 15, "Chennai", "Bangalore", java.sql.Date.valueOf("2022-08-01"), false, true));
        passengers.add(new PassengerInfo(151331, "Keshava", 'M', 45, "Chennai", "Bangalore", java.sql.Date.valueOf("2022-08-01"), true, false));
        passengers.add(new PassengerInfo(151332, "Hindu", 'F', 10, "Chennai", "Goa", java.sql.Date.valueOf("2022-08-03"), false, false));

    }

    @Test
    public void deleteDataFromTables() {
        TablesDAO tablesDAO = new TablesDAO();
        assertTrue(tablesDAO.deleteDataFromTables());
    }

    @Test
    void writeCsvDetailsToDB() {
        assertTrue(manager.writeCsvDetailsToDB());
    }

    @Test
    public void loadBusMasterList() {
        assertTrue(manager.loadBusMasterList());
    }

    @Test
    public void bookAndCancelCommonTicket() {

        //Passenger travelling from Chennai to Bangalore on 01-Aug-2022

        assertEquals(26, busDetailsDAO.getAvailableCommonSeats("122S"));
        //It has to  book ticket in BUS_NO "122S"
        System.out.println("- - - Booking Common seat ticket from Chennai to Bangalore on 2022-08-01 - - - ");
        Reservation reservation = manager.bookTicket(new PassengerInfo(151331, "Kowlutla", 'M', 21, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), false, false));

        System.out.println(reservation);
        //Ticket bust book in Bus With Bus No :122S
        assertEquals("122S", reservation.getBusMaster().getBusNo());

        //Now available common seats in "122S" bus is decreased by 1
        assertEquals(25, busDetailsDAO.getAvailableCommonSeats("122S"));

        System.out.println("\n* * * Cancelling Reservation * * * * ");
        //Now I'm Cancelling the reservation, So available common Tickets will be increment by 1
        manager.cancelTicket(reservation);
        assertEquals(26, busDetailsDAO.getAvailableCommonSeats("122S"));
    }

    @Test
    public void bookAndCancelWomanOnlySeats() {
        //Passenger travelling from Chennai to Bangalore on 01-Aug-2022
        //manager.loadBusMasterList();

        assertEquals(4, busDetailsDAO.getAvailableWomanSeats("122S"));
        //It has to  book ticket in BUS_NO "122S" in womanOnlySeat Category
        System.out.println("- - - Booking Woman Only seat ticket from Chennai to Bangalore on 2022-08-01 - - - ");
        Reservation reservation = manager.bookTicket(new PassengerInfo(151337, "Sudeepthi", 'F', 21, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), false, true));
        System.out.println(reservation);

        //Ticket bust book in Bus With Bus No :122S
        assertEquals("122S", reservation.getBusMaster().getBusNo());

        //Now available woman seats in "122S" bus is decreased by 1
        assertEquals(3, busDetailsDAO.getAvailableWomanSeats("122S"));

        //Now I'm Cancelling the reservation, So available Woman Only Seat will be increment by 1
        System.out.println("\n* * * Cancelling Reservation * * * * ");
        manager.cancelTicket(reservation);
        assertEquals(4, busDetailsDAO.getAvailableWomanSeats("122S"));
    }

    @Test
    public void bookAndCancelSpecialSeats() {
        //Passenger travelling from Chennai to Bangalore on 01-Aug-2022

        assertEquals(2, busDetailsDAO.getAvailableSpecialSeats("146R"));
        System.out.println("- - - Booking Special seat ticket from Madurai to Bangalore on 2022-08-02 - - - ");
        //It has to  book ticket in BUS_NO "122S" in Special Seat category
        Reservation reservation = manager.bookTicket(new PassengerInfo(151330, "Deekshita", 'F', 21, "Madurai", "Bangalore", Date.valueOf("2022-08-02"), true, false));
        System.out.println(reservation);
        //Ticket bust book in Bus With Bus No :122S
        assertEquals("146R", reservation.getBusMaster().getBusNo());
        //Now available woman seats in "122S" bus is decreased by 1
        assertEquals(1, busDetailsDAO.getAvailableSpecialSeats("146R"));

        System.out.println("\n* * * Cancelling Reservation * * * * ");
        //Now I'm Cancelling the reservation, So available Special Tickets will be increment by 1
        manager.cancelTicket(reservation);
        assertEquals(2, busDetailsDAO.getAvailableSpecialSeats("146R"));
    }


    @Test
    public void bookingNotPossibleDueToLackOfTickets() {
        assertEquals(2, busDetailsDAO.getAvailableSpecialSeats("122S"));
        //It has to  book ticket in BUS_NO "122S" in Special Seat category

        System.out.println("- - - Booking Special seat ticket from Chennai to Bangalore on 2022-08-01 - - - ");
        Reservation reservation = manager.bookTicket(new PassengerInfo(151337, "Sudeepthi", 'F', 21, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), true, false));
        //print reservation details
        System.out.println(reservation);
        System.out.println();
        //Ticket bust book in Bus With Bus No :122S
        assertEquals("122S", reservation.getBusMaster().getBusNo());
        //Now available woman seats in "122S" bus is decreased by 1
        assertEquals(1, busDetailsDAO.getAvailableSpecialSeats("122S"));

        System.out.println("- - - Booking Special seat ticket from Chennai to Bangalore on 2022-08-01 - - - ");
        //It has to  book ticket in BUS_NO "122S" in Special Seat category
        Reservation reservation1 = manager.bookTicket(new PassengerInfo(151335, "Deekshita", 'F', 21, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), true, false));
        System.out.println(reservation1);
        System.out.println();

        //Ticket bust book in Bus With Bus No :122S
        assertEquals("122S", reservation1.getBusMaster().getBusNo());
        //Now available woman seats in "122S" bus is decreased by 1
        assertEquals(0, busDetailsDAO.getAvailableSpecialSeats("122S"));
        assertThrows(LackOfSeatsException.class, () -> manager.bookTicket(new PassengerInfo(151331, "Kowlutla", 'M', 21, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), true, false)));

        System.out.println("\n* * * Cancelling all Reservations * * * ");
        manager.cancelTicket(reservation);
        manager.cancelTicket(reservation1);

    }

    @Test
    public void bookingNotPossibleDueToLackOfBusFromStartToEnd() {
        //We are choosing the start,end and date where buses are not available
        assertThrows(BusNotFoundException.class, () -> manager.bookTicket(new PassengerInfo(151337, "Sudeepthi", 'F', 21, "Chennai", "P.Kotakonda", Date.valueOf("2022-08-01"), true, false)));
    }

    @Test
    public void suggestAlternateTravel() {

        System.out.println("Alternate Travel to reach Chennai to Madurai on 2022-08-04");

        //List that hold the alternative way to reach source to destination on passenger preffered date
        List<BusMaster> alternateBuses = manager.suggestAlternateTravelPlan((new PassengerInfo(151335, "Deekshita", 'F', 21, "Chennai", "Madurai", Date.valueOf("2022-08-04"), true, false)));
        for (BusMaster busMaster : alternateBuses) {
            System.out.println(busMaster);
        }
    }

    @Test
    public void getAllReservationsForBus() {
        //It has to  book ticket in BUS_NO "122S" in Special Seat category
        Reservation reservation = manager.bookTicket(new PassengerInfo(151337, "Sudeepthi", 'F', 20, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), true, false));
        //Ticket bust book in Bus With Bus No :122S
        assertEquals("122S", reservation.getBusMaster().getBusNo());

        reservation = manager.bookTicket(new PassengerInfo(151335, "Deekshita", 'F', 15, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), false, true));
        reservation = manager.bookTicket(new PassengerInfo(151330, "Kowlutla", 'M', 21, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), false, false));

        List<Reservation> reservations = manager.getAllReservationsForBus("122S");
        System.out.println("All Reservations Of Bus 122S");
        for (Reservation reserve : reservations) {
            System.out.println(reserve);
            System.out.println();
        }

        //Cancelling all reservations
        for (Reservation reserve : reservations) {
            manager.cancelTicket(reserve);
        }
    }

    @Test
    public void testWriteAllReservationsToFile() throws Exception {

        //It has to  book ticket in BUS_NO "122S" in Special Seat category
        Reservation reservation = manager.bookTicket(new PassengerInfo(151337, "Sudeepthi", 'F', 20, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), false, false));
        reservation = manager.bookTicket(new PassengerInfo(151335, "Deekshita", 'F', 15, "Chennai", "Goa", Date.valueOf("2022-08-03"), false, true));
        reservation = manager.bookTicket(new PassengerInfo(151330, "Kowlutla", 'M', 21, "Chennai", "Trichy", Date.valueOf("2022-08-04"), false, false));
        reservation = manager.bookTicket(new PassengerInfo(151331, "Hindu", 'F', 10, "Trichy", "Madurai", Date.valueOf("2022-08-05"), false, false));
        reservation = manager.bookTicket(new PassengerInfo(151334, "Hari", 'M', 24, "Chennai", "Bangalore", Date.valueOf("2022-08-01"), true, false));


        assertTrue(manager.writeAllReservationsToFile());
    }
}