package client;

import connection.DBConnection;
import dao.BusDetailsDAO;
import exceptions.AlreadBookedException;
import exceptions.BusNotFoundException;
import exceptions.LackOfSeatsException;
import model.BusDetail;
import model.BusMaster;
import model.PassengerInfo;
import model.Reservation;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationManager {

    //list that hold BusMaster Objects
    List<BusMaster> busMasterList;
    //list that hold PassengerInfo Objects
    List<PassengerInfo> currentPassengerList;
    //list that hold Reservation Objects
    List<Reservation> currentReservationList;

    //BusDetailsDAO object that contain method to operate on BusDetails Table
    private BusDetailsDAO busDetailsDAO;

    //No Argument Constructor
    public ReservationManager() {
        busMasterList = new ArrayList<>();
        currentReservationList = new ArrayList<>();
        currentPassengerList = new ArrayList<>();
        busDetailsDAO = new BusDetailsDAO();
    }

    //Getter method to return busMaster list
    public List<BusMaster> getBusMasterList() {
        return busMasterList;
    }

    public void setBusMasterList(List<BusMaster> busMasterList) {
        this.busMasterList = busMasterList;
    }

    //Getter method to return current PassengerInfo list
    public List<PassengerInfo> getCurrentPassengerList() {
        return currentPassengerList;
    }

    //Setter method for current Passengers list
    public void setCurrentPassengerList(List<PassengerInfo> currentPassengerList) {
        this.currentPassengerList = currentPassengerList;
    }

    //Getter method to return current Reservation list
    public List<Reservation> getCurrentReservationList() {
        return currentReservationList;
    }

    //Setter method for current Reservation list
    public void setCurrentReservationList(List<Reservation> currentReservationList) {
        this.currentReservationList = currentReservationList;
    }

    /**
     * 1.Create a method writeCsvDetailsToDB(). This method should write
     * the valid row details of the aforementioned CSV files into their
     * relevant tables.
     */
    public boolean writeCsvDetailsToDB() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            //Create BufferReader object to read csv data from "Bus-Details.csv"
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Bus-Details.csv")));

            //Get the connection Object by Using DBConnection Class
            connection = DBConnection.getDBConnection();

            //statement to insert values into bus_details
            preparedStatement = connection.prepareStatement("INSERT INTO bus_details values(?,?,?,?,?,?,?,?);");

            //list to hold invalid bus details
            List<BusDetail> invalidBusDetails = new ArrayList<>();
            String line = "";
            //read the first line which is heading of columns
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                //read the line and split it by comma(,)
                String[] attrubutes = line.split(",");

                //get the data from each line store in variables
                String busNo = attrubutes[0];
                int totalSeats = Integer.parseInt(attrubutes[1]);
                int totalCommonSeats = Integer.parseInt(attrubutes[2]);
                int totalWomanSeats = Integer.parseInt(attrubutes[3]);
                int totalSpecialSeats = Integer.parseInt(attrubutes[4]);
                int availableCommonSeats = Integer.parseInt(attrubutes[5]);
                int availableWomanSeats = Integer.parseInt(attrubutes[6]);
                int availableSpecialSeats = Integer.parseInt(attrubutes[7]);

                //Create BusDetails Object with above readed data
                BusDetail busDetail = new BusDetail(busNo, totalSeats, totalCommonSeats, totalWomanSeats, totalSpecialSeats, availableCommonSeats, availableWomanSeats, availableSpecialSeats);

                //Check whether BusDetail the data is valid or not
                if (ReservationManagerUtil.isValid(busDetail)) {
                    //if valid data then store it in Bus Detail Table in database
                    preparedStatement.setString(1, busNo);
                    preparedStatement.setInt(2, totalSeats);
                    preparedStatement.setInt(3, totalCommonSeats);
                    preparedStatement.setInt(4, totalWomanSeats);
                    preparedStatement.setInt(5, totalSpecialSeats);
                    preparedStatement.setInt(6, availableCommonSeats);
                    preparedStatement.setInt(7, availableWomanSeats);
                    preparedStatement.setInt(8, availableSpecialSeats);
                    preparedStatement.executeUpdate();

                } else {
                    //if invalid data add that BusDetail to invalid BusDetails List
                    invalidBusDetails.add(busDetail);
                }
            }


            //if any invalid BusDetails read from CSV file
            if (invalidBusDetails.size() > 0) {
                //print those details in error.txt file
                PrintWriter printWriter = new PrintWriter(new FileWriter(new File("error.txt")));
                for (BusDetail invalidBus : invalidBusDetails) {
                    printWriter.println(invalidBus);
                }
                System.out.println(" - - - Invalid Details are logged to error.txt file - - - ");
                printWriter.close();
            }

            //bufferedReader object to read data from Bus-Master.csv file
            bufferedReader = new BufferedReader(new FileReader(new File("Bus-Master.csv")));
            //Create a statement and provide query to insert data into Bus_master table
            preparedStatement = connection.prepareStatement("INSERT INTO BUS_MASTER VALUES(?,?,?,?,?,?,?,?)");
            //read the first line (to skip to store in database)
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                // read the each line and split it by Comma(,)
                String[] attributes = line.split(",");
                //store the all data in bus_master table
                preparedStatement.setString(1, attributes[0]);
                preparedStatement.setString(2, attributes[1]);
                preparedStatement.setString(3, attributes[2]);
                String[] values = attributes[3].split("-");
                int year = Integer.parseInt(values[2]);
                int day = Integer.parseInt(values[0]);
                //Convert month name to month number
                int month = ReservationManagerUtil.getMonthNumberByName(values[1]);
                java.sql.Date sdate = java.sql.Date.valueOf("20" + values[2] + "-" + month + "-" + values[0]);
                preparedStatement.setDate(4, sdate);
                preparedStatement.setTime(5, Time.valueOf(attributes[4] + ":00"));
                preparedStatement.setString(6, attributes[5]);
                preparedStatement.setInt(7, Integer.parseInt(attributes[6]));
                preparedStatement.setString(8, attributes[7]);
                //execute the query
                preparedStatement.executeUpdate();
            }

            //close the connections
            preparedStatement.close();
            connection.close();
            //return true as we inserted all data into tables
            return true;
        }
        //handling the exceptions
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //return false as not inserted all data into tables
        return false;
    }


    /*
        Utility method to load the all Bus Details from bus_details table
        and store in busDetailList which is passed as argument
     */
    private boolean loadBusDetailList(List<BusDetail> busDetailList) {
        Connection connection;
        Statement statement;

        try {
            //get the connection by using DBConnection Class
            connection = DBConnection.getDBConnection();
            //create statement object
            statement = connection.createStatement();
            //create query to get all data from bus_details
            String query = "SELECT *FROM bus_details;";
            //Execute query
            ResultSet resultSet = statement.executeQuery(query);
            BusDetail busDetail;
            //read the all data from bus details table
            while (resultSet.next()) {
                //create BusDetal object
                busDetail = new BusDetail();
                //set the all values to BusDetail Object using setter methods
                busDetail.setBusNo(resultSet.getString(1));
                busDetail.setTotalSeats(resultSet.getInt(2));
                busDetail.setTotalCommonSeats(resultSet.getInt(3));
                busDetail.setTotalWomanOnlySeats(resultSet.getInt(4));
                busDetail.setTotalSpecialSeats(resultSet.getInt(5));
                busDetail.setAvailableCommonSeats(resultSet.getInt(6));
                busDetail.setAvailableWomanSeats(resultSet.getInt(7));
                busDetail.setAvailableSpecialSeats(resultSet.getInt(8));

                //add busDetail to busDetailsList
                busDetailList.add(busDetail);
            }

            //close the connection
            statement.close();
            connection.close();
            //return true as we read all data from bus_detail table
            return true;
        }
        //handling exception
        catch (SQLException e) {
            e.printStackTrace();
            //return false as we didn't read all data from bus_detail table
            return false;
        }
    }

    /**
     * 2.Create a method loadBusMasterList().
     * This method should read from the Bus Master table
     * and create a list of BusMaster objects.
     * This method should also call another method you will code
     * – loadBusDetailList() that will read from Bus Details table
     * and associate the correct BusDetail object to the BusMaster
     * object [look for the busNumber attribute in both objects for this].
     * The return type of both methods is void.
     */
    public boolean loadBusMasterList() {

        //Create list to store the BusDetail Object
        List<BusDetail> busDetailList = new ArrayList<>();
        //pass busDetail list to method to get all bus details data from database
        loadBusDetailList(busDetailList);

        Connection connection;
        Statement statement;
        try {
            //get the connection by using DBConnection Class
            connection = DBConnection.getDBConnection();
            //create statement object
            statement = connection.createStatement();
            //write query
            String query = "SELECT *FROM bus_master;";
            //execute query
            ResultSet resultSet = statement.executeQuery(query);
            BusMaster busMaster;
            ////read the all data from bus master detail
            while (resultSet.next()) {
                //Create BusMaster Object
                busMaster = new BusMaster();
                //set the all values to BusMaster Object using setter methods
                String busNo = resultSet.getString(1);
                String from = resultSet.getString(2);
                String to = resultSet.getString(3);
                java.util.Date startDate = resultSet.getDate(4);
                Time startTime = resultSet.getTime(5);
                String journeyTime = resultSet.getString(6);
                int totalStops = resultSet.getInt(7);
                String type = resultSet.getString(8);
                busMaster.setBusNo(busNo);
                busMaster.setFrom(from);
                busMaster.setTo(to);
                busMaster.setStartingDate(startDate);
                busMaster.setStartingTime(LocalTime.parse(startTime.toString()));
                busMaster.setJourneyTime(journeyTime);
                busMaster.setTotalStops(totalStops);
                busMaster.setType(type);

                //Check for Same Bus With Bus Name
                for (BusDetail b : busDetailList) {

                    //if we found Bus with same bus name in busDetails list then map that BusDetail for BusMaster
                    if (b.getBusNo().equalsIgnoreCase(busNo)) {
                        busMaster.setBusDetail(b);
                    }
                }
                //add BusMaster to busMasterList
                busMasterList.add(busMaster);
            }

            //Close the all connections
            statement.close();
            connection.close();
            //return true as we read all data from Bus_master table and map busDetail Object
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //return true as we didn't read all data from bus_master table
            return false;
        }

    }

    /**
     * Method that takes passengerInfo and Book ticket and
     *
     * @return Reservation Object
     */
    public Reservation bookTicket(PassengerInfo passengerInfo) {
        boolean presentAlready = false;
        //Checking whether the Given Passenger is already in currentPassengerList
        for (PassengerInfo passenger : currentPassengerList) {
            if (passenger.getPassengerId() == passengerInfo.getPassengerId()) {
                //if already present throw exception
                System.out.println("Alread Ticket Reserved");
                throw new AlreadBookedException("Already Booked");
            }
        }

        //if already not booked, add Passenger to passengers list
        currentPassengerList.add(passengerInfo);
        for (BusMaster bus : busMasterList) {

            //Checking bus is available with date, starting point and ending point
            if (passengerInfo.getTravelDate().compareTo(bus.getStartingDate()) == 0 &&
                    passengerInfo.getStartingPoint().equalsIgnoreCase(bus.getFrom()) &&
                    passengerInfo.getEndingPoint().equalsIgnoreCase(bus.getTo())) {
                //if passenger prefers woman only seat
                if (passengerInfo.isWomanOnlySeatNeeded()) {
                    //if woman seats available in bus
                    if (bus.getBusDetail().getAvailableWomanSeats() > 0) {
                        //get current available woman only seats
                        int currentWomanSeats = bus.getBusDetail().getAvailableWomanSeats();
                        //Decrement available woman seats in busMaster of busMasterLists
                        bus.getBusDetail().setAvailableWomanSeats(currentWomanSeats - 1);
                        //Decrement available woman seats in bus details table
                        busDetailsDAO.decreaseWomanOnlySeats(bus.getBusNo());
                        //Create Reservation Object
                        Reservation reservation = new Reservation();
                        //set the values using Setter methods
                        reservation.setBusMaster(bus);
                        reservation.setPassengerInfo(passengerInfo);
                        //add Reservation to currentReservationList
                        currentReservationList.add(reservation);
                        //return the resveration Object
                        return reservation;
                    } else {
                        //if woman seats not available throw Exception
                        System.out.println(":( :( WOman Only Seats not available :( :( ");
                        throw new LackOfSeatsException("Woman Only Seats Not Available");
                    }
                }
                //if passenger prefer Special seat
                else if (passengerInfo.isSpecialSeatNeeded()) {
                    //if special seats available in bus
                    if (bus.getBusDetail().getAvailableSpecialSeats() > 0) {
                        //Create Reservation Object
                        Reservation reservation = new Reservation();
                        //get current available special seats
                        int currentSpecialSeats = bus.getBusDetail().getAvailableSpecialSeats();
                        //decrement special seats by 1 in bus of busMaster list
                        bus.getBusDetail().setAvailableSpecialSeats(currentSpecialSeats - 1);
                        //decrement special seats by 1 in bus of bus details table
                        busDetailsDAO.decreaseSpecialSeats(bus.getBusNo());
                        //set the values using setter methods
                        reservation.setBusMaster(bus);
                        reservation.setPassengerInfo(passengerInfo);
                        //add Reservation Object to currentReservationList
                        currentReservationList.add(reservation);
                        //return reservation Object
                        return reservation;
                    }
                    //if special seats not available in bus
                    else {
                        //throw exception
                        System.out.println(":( :( Special Seats not available :( :( ");
                        throw new LackOfSeatsException("Special Seats Not Available");
                    }
                } else {
                    //if passenger doesn't prefer any special or woman seat, then it is common seat
                    if (bus.getBusDetail().getAvailableCommonSeats() > 0) {
                        //Create Reservation Object
                        Reservation reservation = new Reservation();
                        //get current available common seats
                        int currentCommonSeats = bus.getBusDetail().getAvailableCommonSeats();
                        //decrement common seats by 1 in bus of busMaster list
                        bus.getBusDetail().setAvailableSpecialSeats(currentCommonSeats - 1);
                        //decrement special seats by 1 in bus of bus Details table
                        busDetailsDAO.decreseCommonSeats(bus.getBusNo());

                        //set the values using Setter methods
                        reservation.setBusMaster(bus);
                        reservation.setPassengerInfo(passengerInfo);
                        //add Reservation Object to currentReservationList
                        currentReservationList.add(reservation);
                        //return the Reservation Object
                        return reservation;

                    } else//throw exception if common seats are not available
                    {
                        System.out.println(":( :( Common Seats not available :( :( ");
                        throw new LackOfSeatsException("Common Seats Not available");
                    }
                }
            }
        }
        //throw exception if bus not found with passenger provided date,starting and ending point
        System.out.println(":( :(  Bus Not Available from " + passengerInfo.getStartingPoint() + " to " + passengerInfo.getEndingPoint() + " on " + passengerInfo.getTravelDate() + " :( :(");
        throw new BusNotFoundException("Bus Not Available from " + passengerInfo.getStartingPoint() + " to " + passengerInfo.getEndingPoint() + " on " + passengerInfo.getTravelDate());
    }

    /**
     * Method to Cancel the reservation ticket
     */
    public void cancelTicket(Reservation reservation) {

        boolean isReserved = false;
        //Check for Reservation in current Reservation List
        for (Reservation res : currentReservationList) {
            //if busno and passenger id are same then mark true as reserved
            if (res.getBusMaster().getBusNo().equalsIgnoreCase(reservation.getBusMaster().getBusNo()) && res.getPassengerInfo().getPassengerId() == reservation.getPassengerInfo().getPassengerId()) {
                isReserved = true;
                break;
            }
        }

        //if given reservation not reserved then display message
        if (!isReserved) {
            System.out.println("Reservation Doesn't Exist");
        }
        //If Reservation found in currentReservationList
        else {
            //get the passenger information
            PassengerInfo passenger = reservation.getPassengerInfo();
            BusMaster busMaster = reservation.getBusMaster();
            //if passenger reserved ticket in woman only category
            if (passenger.isWomanOnlySeatNeeded()) {

                //get the available Woman Seats of Bus
                int availableWomanSeats = busMaster.getBusDetail().getAvailableWomanSeats();

                //Increase available Woman Seats of Bus in busMaster
                busMaster.getBusDetail().setAvailableWomanSeats(availableWomanSeats + 1);

                //Increase the availability of woman seats in Database of Bus
                busDetailsDAO.increaseWomanOnlySeats(busMaster.getBusNo());
                //Display message
                System.out.println("Reservation Cancelled");
                //System.out.println(reservation.getBusMaster().getBusNo() + " Woman Seats Increased ");

            }
            //if passenger booked special Seat
            else if (passenger.isSpecialSeatNeeded()) {
                //get the available Spec Seats of Bus
                int availableSpecialSeats = busMaster.getBusDetail().getAvailableSpecialSeats();
                busMaster.getBusDetail().setAvailableWomanSeats(availableSpecialSeats + 1);
                busDetailsDAO.increseSpecialSeats(busMaster.getBusNo());
                System.out.println("Reservation Cancelled");
                //System.out.println(reservation.getBusMaster().getBusNo() + " Special Seats Increased ");
            } else {
                int availableCommonSeats = busMaster.getBusDetail().getAvailableCommonSeats();
                busMaster.getBusDetail().setAvailableWomanSeats(availableCommonSeats + 1);
                busDetailsDAO.increaseCommonSeats(busMaster.getBusNo());
                System.out.println("Reservation Cancelled");
                // System.out.println(reservation.getBusMaster().getBusNo() + " Common Seats Increased ");
            }
        }

    }

    /**
     * return a list of reservation objects, based on the bus number passed as input.
     * Write test-cases for both positive and negative scenario.
     */
    public List<Reservation> getAllReservationsForBus(String busNumber) {
        //return all reservations made on given bus number
        return currentReservationList.stream()
                .filter(reservation -> reservation.getBusMaster().getBusNo().equalsIgnoreCase(busNumber))
                .collect(Collectors.toList());
    }

    /**
     * method suggestAlternateTravelPlan(PassengerInfo passengerInfo).
     * This method should return a collection of BusMaster objects.
     */
    public List<BusMaster> suggestAlternateTravelPlan(PassengerInfo passengerInfo) {
        //list to return matching busses with passenger provided source and destination point
        List<BusMaster> alternateBusMasterList = busMasterList.stream()
                .filter(busMaster -> busMaster.getFrom().equals(passengerInfo.getStartingPoint()))
                .filter(busMaster -> busMaster.getFrom().equals(passengerInfo.getEndingPoint()))
                .collect(Collectors.toList());

        //list to return busMatchingStartingPointOnly of passenger provided starting point
        List<BusMaster> busMatchingStartingPointOnly = busMasterList.stream()
                .filter(busMaster -> busMaster.getFrom().equals(passengerInfo.getStartingPoint()))
                .filter(busMaster -> !busMaster.getTo().equals(passengerInfo.getEndingPoint()))
                .collect(Collectors.toList());

        //list to return busMatchingEndingPointOnly of passenger provided ending point
        List<BusMaster> busMatchingEndingPointOnly = busMasterList.stream()
                .filter(busMaster -> busMaster.getTo().equals(passengerInfo.getEndingPoint()))
                .filter(busMaster -> !busMaster.getFrom().equals(passengerInfo.getStartingPoint()))
                .collect(Collectors.toList());

        //checking available busses from starting point and reach ending point by using intermediate locations
        for (BusMaster busMaster : busMatchingStartingPointOnly) {
            BusMaster busWithMatchingStartPoint = busMatchingEndingPointOnly.stream()
                    .filter(busMaster1 -> busMaster1.getFrom().equals(busMaster.getTo()))
                    .findAny()
                    .orElse(null);
            if (busWithMatchingStartPoint != null) {
                alternateBusMasterList.add(busMaster);
                alternateBusMasterList.add(busWithMatchingStartPoint);
            }
        }
        //return list of busses that help to reach from source to destination required for passenger
        return alternateBusMasterList;
    }

    /**
     * method writeAllReservationsToFile() which should take the
     * currentReservationList attribute and write that data in to a file called “Reservation.csv”
     */
    public boolean writeAllReservationsToFile() {

        try {
            //create PrintWriter Object that points to "Reservation.csv"
            PrintWriter printWriter = new PrintWriter(new FileWriter(new File("Reservation.csv"), false));
            //take all columns
            String columns = "BusId, From, To, Date, Time, Pass Name, Age, Sex, Seat Number, Seat Preference";
            //print all columns to Reservation.csv file as names of columns
            printWriter.println(columns);
            //for all Reservations in currentReservationList
            for (Reservation reservation : currentReservationList) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                //take all data as same as column names
                String result = reservation.getBusMaster().getBusNo() +
                        "," + reservation.getBusMaster().getFrom() +
                        "," + reservation.getBusMaster().getTo() +
                        "," + formatter.format(reservation.getBusMaster().getStartingDate()) +
                        "," + reservation.getBusMaster().getStartingTime() +
                        "," + reservation.getPassengerInfo().getName() +
                        "," + reservation.getPassengerInfo().getAge() +
                        "," + ((reservation.getPassengerInfo().getSex() == 'M' || reservation.getPassengerInfo().getSex() == 'm') ? "M" : "F") +
                        "," + reservation.getSeatNumber();
                result += ",";
                if (reservation.getPassengerInfo().isWomanOnlySeatNeeded()) {
                    result += "Woman ";
                } else if (reservation.getPassengerInfo().isSpecialSeatNeeded()) {
                    result += "Special";
                } else {
                    result += "Normal";
                }
                //print the reservation data to file
                printWriter.println(result);

            }
            //close the print writer
            printWriter.close();
            //return true as we write all currentReservationList to file
            return true;
        } catch (IOException e) {
            //handling exceptions
            e.printStackTrace();
            //return true as we didn't write all currentReservationList to file
            return false;
        }
    }
}
