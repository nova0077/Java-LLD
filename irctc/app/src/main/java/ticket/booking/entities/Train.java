package main.java.ticket.booking.entities;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Train {
  private String trainId;
  private String trainNo;
  private List<List<Integer>> seats;
  private Map<String, String> stationTimes;
  private List<String> stations;

  public Train() {
  }

  public Train(String trainId, String trainNo, List<List<Integer>> seats, Map<String, String> stationTimes,
      List<String> stations) {
    this.trainId = trainId;
    this.trainNo = trainNo;
    this.seats = seats;
    this.stationTimes = stationTimes;
    this.stations = stations;
  }

  public String getTrainId() {
    return trainId;
  }

  public void setTrainId(String trainId) {
    this.trainId = trainId;
  }

  public List<List<Integer>> getSeats() {
    return seats;
  }

  public void setSeats(List<List<Integer>> seats) {
    this.seats = seats;
  }

  public Map<String, String> getStationTimes() {
    return stationTimes;
  }

  public void setStationTimes(Map<String, String> stationTimes) {
    this.stationTimes = stationTimes;
  }

  public List<String> getStations() {
    return stations;
  }

  public void setStations(List<String> stations) {
    this.stations = stations;
  }

  public String getTrainNo() {
    return trainNo;
  }

  public void setTrainNo(String trainNo) {
    this.trainNo = trainNo;
  }

  @JsonIgnore
  public String getTrainInfo() {
    return "Train Id: " + trainId + " Train No: " + trainNo;
  }

  public Integer getCountOfAvailableSeats(List<List<Integer>> seats) {
    return (int) seats.stream()
        .flatMap(inner -> inner.stream())
        .filter(num -> num == 0)
        .count();
  }
}
