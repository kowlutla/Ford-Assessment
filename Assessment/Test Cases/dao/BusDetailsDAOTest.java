package dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusDetailsDAOTest {

    BusDetailsDAO busDetailsDAO;

    @BeforeEach
    public void setUp() {
        busDetailsDAO = new BusDetailsDAO();
    }

    @Test
    void testAvailableCommonSeats() {
        assertEquals(26, busDetailsDAO.getAvailableCommonSeats("122S"));
        busDetailsDAO.decreseCommonSeats("122S");
        assertEquals(25, busDetailsDAO.getAvailableCommonSeats("122S"));
        busDetailsDAO.increaseCommonSeats("122S");
        assertEquals(26, busDetailsDAO.getAvailableCommonSeats("122S"));
    }

    @Test
    void testAvailableWomanSeats() {
        assertEquals(4, busDetailsDAO.getAvailableWomanSeats("122S"));
        busDetailsDAO.decreaseWomanOnlySeats("122S");
        assertEquals(3, busDetailsDAO.getAvailableWomanSeats("122S"));
        busDetailsDAO.increaseWomanOnlySeats("122S");
        assertEquals(4, busDetailsDAO.getAvailableWomanSeats("122S"));
    }

    @Test
    void testAvailableSpecialSeats() {
        assertEquals(2, busDetailsDAO.getAvailableSpecialSeats("122S"));
        busDetailsDAO.decreaseSpecialSeats("122S");
        assertEquals(1, busDetailsDAO.getAvailableSpecialSeats("122S"));
        busDetailsDAO.increseSpecialSeats("122S");
        assertEquals(2, busDetailsDAO.getAvailableSpecialSeats("122S"));
    }
}