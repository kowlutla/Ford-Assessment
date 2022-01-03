package dao;

import connection.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class contain code to create tables require for application
 */
public class TablesDAO {

    /**
     * Method to create BusMaster table and
     *
     * @return true if it create tables
     * Otherwise return false
     */
    public boolean createBusMasterTable() {
        Connection connection = DBConnection.getDBConnection();
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE bus_master (bus_no varchar(10) primary key,from_location varchar(30),to_location varchar(30),starting_date date,starting_time time," +
                    "journey_time varchar(15),total_stops int, type varchar(20));";
            statement.executeUpdate(query);
            statement.close();
            connection.createStatement();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Method to create BusMaster table and
     *
     * @return true if it create tables
     * Otherwise return false
     */
    public boolean createBusDetailsTable() {
        Connection connection = DBConnection.getDBConnection();
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE bus_details (bus_no varchar(10) primary key , total_seats int, total_common_seats int," +
                    "total_woman_only_seats int, total_special_seats int, available_common_seats int, available_woman_Seats int ," +
                    "available_special_seats int);";
            statement.executeUpdate(query);
            statement.close();
            connection.createStatement();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDataFromTables() {
        Connection connection = DBConnection.getDBConnection();
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            String query = "delete from bus_master where 1=1";
            statement.executeUpdate(query);
            query = "delete from bus_details where 1=1";
            statement.executeUpdate(query);
            statement.close();
            connection.createStatement();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
