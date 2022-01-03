package dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TablesDAOTest {

    TablesDAO createTablesDAO;

    @BeforeEach
    void setUp() {
        createTablesDAO = new TablesDAO();
    }

    @Test
    void createBusMasterTable() {
        assertTrue(createTablesDAO.createBusMasterTable());
        System.out.println("Bus Master table Created ");
    }

    @Test
    void createBusDetailsTable() {
        assertTrue(createTablesDAO.createBusDetailsTable());
        System.out.println("Bus Details table Created");
    }
}