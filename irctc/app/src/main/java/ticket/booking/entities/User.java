package main.java.ticket.booking.entities;

import java.util.*;

import javax.net.ssl.SSLSocket;

public class User {
  private String name;
  private String password;
  private String hashedPassword;
  private List<Ticket> ticketsBooked;
  private String userId;

  public User(String name, String password, String hashedPassword, List<Ticket> ticketsBooked, String userId) {
    this.name = name;
    this.password = password;
    this.hashedPassword = hashedPassword;
    this.ticketsBooked = ticketsBooked;
    this.userId = userId;
  }

  public User() {
  }

  public String getName() {
    return this.name;
  }

  public String getPassword() {
    return this.password;
  }

  public String getHashedPassword() {
    return this.hashedPassword;
  }

  public List<Ticket> getTicketsBooked() {
    return this.ticketsBooked;
  }

  public void printTickets() {
    if (ticketsBooked.size() == 0)
      System.out.println("No past bookings on your username");
    else {
      for (int i = 0; i < ticketsBooked.size(); i++) {
        if(!ticketsBooked.get(i).getStatus().equals("canceled"))
          System.out.println(i+1 + ": " +ticketsBooked.get(i).getTicketInfo());
      }
    }
  }

  public String getUserId() {
    return this.userId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  public void setTicketsBooked(List<Ticket> ticketsBooked) {
    this.ticketsBooked = ticketsBooked;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
