package model;

import lombok.Data;
import validation.BusNameAnnotation;
import validation.SeatNumbersAnnotation;

@Data
public class BusDetail {

    //  @Pattern(regexp="^[0-9]{3}[a-zA-z]",message = "should contain three digits and a alphabet")
    @BusNameAnnotation
    private String busNo;
    @SeatNumbersAnnotation
    private int totalSeats;
    @SeatNumbersAnnotation
    private int totalCommonSeats;
    @SeatNumbersAnnotation
    private int totalWomanOnlySeats;
    @SeatNumbersAnnotation
    private int totalSpecialSeats;
    @SeatNumbersAnnotation
    private int availableCommonSeats;
    @SeatNumbersAnnotation
    private int availableWomanSeats;
    @SeatNumbersAnnotation
    private int availableSpecialSeats;

    //No Argument Constructor
    public BusDetail() {
    }

    //All Argument Constructor
    public BusDetail(String busNo, int totalSeats, int totalCommonSeats, int totalWomanOnlySeats, int totalSpecialSeats, int availableCommonSeats, int availableWomanSeats, int availableSpecialSeats) {
        this.busNo = busNo;
        this.totalSeats = totalSeats;
        this.totalCommonSeats = totalCommonSeats;
        this.totalWomanOnlySeats = totalWomanOnlySeats;
        this.totalSpecialSeats = totalSpecialSeats;
        this.availableCommonSeats = availableCommonSeats;
        this.availableWomanSeats = availableWomanSeats;
        this.availableSpecialSeats = availableSpecialSeats;
    }

    //Setter and Getter methods for all fields
    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getTotalCommonSeats() {
        return totalCommonSeats;
    }

    public void setTotalCommonSeats(int totalCommonSeats) {
        this.totalCommonSeats = totalCommonSeats;
    }

    public int getTotalWomanOnlySeats() {
        return totalWomanOnlySeats;
    }

    public void setTotalWomanOnlySeats(int totalWomanOnlySeats) {
        this.totalWomanOnlySeats = totalWomanOnlySeats;
    }

    public int getTotalSpecialSeats() {
        return totalSpecialSeats;
    }

    public void setTotalSpecialSeats(int totalSpecialSeats) {
        this.totalSpecialSeats = totalSpecialSeats;
    }

    public int getAvailableCommonSeats() {
        return availableCommonSeats;
    }

    public void setAvailableCommonSeats(int availableCommonSeats) {
        this.availableCommonSeats = availableCommonSeats;
    }

    public int getAvailableWomanSeats() {
        return availableWomanSeats;
    }

    public void setAvailableWomanSeats(int availableWomanSeats) {
        this.availableWomanSeats = availableWomanSeats;
    }

    public int getAvailableSpecialSeats() {
        return availableSpecialSeats;
    }

    public void setAvailableSpecialSeats(int availableSpecialSeats) {
        this.availableSpecialSeats = availableSpecialSeats;
    }

    //toString method to return String representation of BusDetail
    @Override
    public String toString() {
        return "BusDetail{" +
                "busNo='" + busNo + '\'' +
                ", totalSeats=" + totalSeats +
                ", totalCommonSeats=" + totalCommonSeats +
                ", totalWomanOnlySeats=" + totalWomanOnlySeats +
                ", totalSpecialSeats=" + totalSpecialSeats +
                ", availableCommonSeats=" + availableCommonSeats +
                ", availableWomanSeats=" + availableWomanSeats +
                ", availableSpecialSeats=" + availableSpecialSeats +
                '}';
    }
}
