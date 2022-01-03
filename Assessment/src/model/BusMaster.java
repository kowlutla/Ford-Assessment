package model;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class BusMaster {

    //Fields of BusMaster
    private String busNo;
    private String from;
    private String to;
    private Date startingDate;
    private LocalTime startingTime;
    private String journeyTime;
    private int totalStops;
    private String type;

    //reference to BusDetail
    private BusDetail busDetail;

    //Getter and Setter for all fields
    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public int getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(int totalStops) {
        this.totalStops = totalStops;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BusDetail getBusDetail() {
        return busDetail;
    }

    public void setBusDetail(BusDetail busDetail) {
        this.busDetail = busDetail;
    }

    //toString method to return String representation of BusMaster
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MMM-dd");
        return "BusMaster{" +
                "busNo='" + busNo + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", startingDate=" + simpleDateFormat.format(startingDate) +
                ", startingTime=" + startingTime +
                ", journeyTime=" + journeyTime +
                ", totalStops=" + totalStops +
                ", type='" + type + '\'' +
                ", busDetail=" + busDetail +
                '}';
    }
}
