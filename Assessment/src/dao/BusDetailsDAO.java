package dao;

import connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusDetailsDAO {

    public boolean decreseCommonSeats(String busNo) {
        int availableCommonSeats = getAvailableCommonSeats(busNo);
        if (availableCommonSeats > 0) {
            Connection connection = DBConnection.getDBConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("update bus_details set available_common_seats = ? where bus_no = ?;");
                statement.setInt(1, availableCommonSeats - 1);
                statement.setString(2, busNo);
                statement.executeUpdate();
                statement.close();
                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public int getAvailableCommonSeats(String busNo) {
        Connection connection = DBConnection.getDBConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("select available_common_seats from BUS_DETAILS where bus_no=?");
            statement.setString(1, busNo);
            ResultSet resultSet = statement.executeQuery();
            int commonSeats = 0;
            if (resultSet.next()) {
                commonSeats = resultSet.getInt(1);
            }

            return commonSeats;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean increaseCommonSeats(String busNo) {
        int availableCommonSeats = getAvailableCommonSeats(busNo);
        if (availableCommonSeats > 0) {
            Connection connection = DBConnection.getDBConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("update bus_details set available_common_seats = ? where bus_no = ?;");
                statement.setInt(1, availableCommonSeats + 1);
                statement.setString(2, busNo);
                statement.executeUpdate();
                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public int getAvailableSpecialSeats(String busNo) {
        Connection connection = DBConnection.getDBConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("select available_special_seats from BUS_DETAILS where bus_no=?");
            statement.setString(1, busNo);
            ResultSet resultSet = statement.executeQuery();
            int specialSeats = 0;
            if (resultSet.next()) {
                specialSeats = resultSet.getInt(1);
            }

            return specialSeats;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean decreaseSpecialSeats(String busNo) {
        int availableSpecialSeats = getAvailableSpecialSeats(busNo);
        if (availableSpecialSeats > 0) {
            Connection connection = DBConnection.getDBConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("update bus_details set available_special_seats = ? where bus_no = ?;");
                statement.setInt(1, availableSpecialSeats - 1);
                statement.setString(2, busNo);
                statement.executeUpdate();
                statement.close();
                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean increseSpecialSeats(String busNo) {
        System.out.println("BUS NO: " + busNo);
        int availableSpecialSeats = getAvailableSpecialSeats(busNo);
        Connection connection = DBConnection.getDBConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("update bus_details set available_special_seats = ? where bus_no = ?;");
            statement.setInt(1, availableSpecialSeats + 1);
            statement.setString(2, busNo);
            statement.executeUpdate();
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean decreaseWomanOnlySeats(String busNo) {
        int availableSpecialSeats = getAvailableWomanSeats(busNo);
        Connection connection = DBConnection.getDBConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("update bus_details set available_woman_seats = ? where bus_no = ?;");
            statement.setInt(1, availableSpecialSeats - 1);
            statement.setString(2, busNo);
            statement.executeUpdate();
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean increaseWomanOnlySeats(String busNo) {
        int availableSpecialSeats = getAvailableWomanSeats(busNo);
        Connection connection = DBConnection.getDBConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("update bus_details set available_woman_seats = ? where bus_no = ?;");
            statement.setInt(1, availableSpecialSeats + 1);
            statement.setString(2, busNo);
            statement.executeUpdate();
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getAvailableWomanSeats(String busNo) {
        Connection connection = DBConnection.getDBConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("select available_woman_seats from BUS_DETAILS where bus_no=?");
            statement.setString(1, busNo);
            ResultSet resultSet = statement.executeQuery();
            int womanSeats = 0;
            if (resultSet.next()) {
                womanSeats = resultSet.getInt(1);
            }

            return womanSeats;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
