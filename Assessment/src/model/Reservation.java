package model;

import java.text.SimpleDateFormat;

public class Reservation {

    //temp field used to allocate Seat number
    private static int temp;

    //Fields of Reservation
    int seatNumber;
    PassengerInfo passengerInfo;
    BusMaster busMaster;


    //No Argument Constructor
    public Reservation() {
        temp++;
        setSeatNumber(temp);
    }

    //Getter and Setter methods of All Fields
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public PassengerInfo getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(PassengerInfo passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public BusMaster getBusMaster() {
        return busMaster;
    }

    public void setBusMaster(BusMaster busMaster) {
        this.busMaster = busMaster;
    }


    //toString method to return String representation of Reservation Object
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String result = "From: " + busMaster.getFrom() +
                " To: " + busMaster.getTo() +
                " Date: " + formatter.format(busMaster.getStartingDate()) +
                " Time: " + busMaster.getStartingTime() +
                " Seat No: " + seatNumber
                + "\n" +
                "Passenger Name: " + passengerInfo.getName() +
                " Age: " + passengerInfo.getAge() +
                " Sex: " + ((passengerInfo.getSex() == 'M' || passengerInfo.getSex() == 'm') ? "Male" : "Female") +
                " Seat Preferences: ";
        if (passengerInfo.isSpecialSeatNeeded() && passengerInfo.isWomanOnlySeatNeeded()) {
            result += "Woman and Special";
        } else if (passengerInfo.isWomanOnlySeatNeeded()) {
            result += "Woman Only";
        } else if (passengerInfo.isSpecialSeatNeeded()) {
            result += "Special";
        } else {
            result += "Common";
        }
        return result;

    }
}
