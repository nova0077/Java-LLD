package main.java.ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class Ticket {
  private String ticketId;
  private Set<List<Integer>> seatsBooked;
  private String userId;
  private String source;
  private String destination;
  private String trainId;
  private String status = "active";

  public Ticket() {
  };

  public Ticket(String ticketId, String userId, String source, String destination, String trainId,
      Set<List<Integer>> seatsBooked) {
    this.ticketId = ticketId;
    this.userId = userId;
    this.source = source;
    this.destination = destination;
    this.trainId = trainId;
    this.seatsBooked = seatsBooked;
    this.status = "active";
  }

  public Set<List<Integer>> getSeatsBooked() {
    return seatsBooked;
  }

  public void setSeatsBooked(Set<List<Integer>> seatsBooked) {
    this.seatsBooked = seatsBooked;
  }

  @JsonIgnore
  public String getTicketInfo() {
    return "Ticket ID: " + ticketId + " belongs to User " + userId + " from " + source + " to " + destination
        + " on train " + trainId;
  }

  public String getTicketId() {
    return ticketId;
  }

  public void setTicketId(String ticketId) {
    this.ticketId = ticketId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTrainId() {
    return trainId;
  }

  public void setTrainId(String trainId) {
    this.trainId = trainId;
  }
}
