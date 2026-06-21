package main.java.ticket.booking.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.ticket.booking.entities.Ticket;
import main.java.ticket.booking.entities.Train;
import main.java.ticket.booking.entities.User;
import main.java.ticket.booking.util.UserServiceUtil;

public class UserBookingService {
  private User user;
  private List<User> userList;
  private TrainService trainService; // Add TrainService reference

  private static final String USERS_PATH = "/Users/praveen/Desktop/code/java/irctc/app/src/main/java/ticket/booking/localDb/users.json";
  private static ObjectMapper objectMapper = new ObjectMapper();

  public List<User> loadUsers() throws IOException {
    File users = new File(USERS_PATH);
    if (!users.exists()) {
      System.out.println("User database file not found: " + users.getAbsolutePath());
      return new ArrayList<>(); // Return empty list if file missing
    }
    return objectMapper.readValue(users, new TypeReference<List<User>>() {
    });
  }

  public UserBookingService() throws IOException {
    userList = loadUsers();
    trainService = new TrainService(); // Initialize TrainService
  }

  public UserBookingService(User user1) throws IOException {
    this.user = user1;
    userList = loadUsers();
    trainService = new TrainService(); // Initialize TrainService
  }

  public User loginUser() {
    Optional<User> foundUser = userList.stream().filter(user1 -> user1.getName().equals(user.getName()) &&
        UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword())).findFirst();

    return foundUser.orElse(null);
  }

  public Boolean signup(User user1) {
    try {
      userList.add(user1);
      saveUserListToFile();
      return Boolean.TRUE;
    } catch (IOException ex) {
      return Boolean.FALSE;
    }
  }

  // json -> obj (Deserialization)
  // obj -> json (Serialization)
  private void saveUserListToFile() throws IOException {
    File usersFile = new File(USERS_PATH);
    objectMapper.writeValue(usersFile, userList);
  }

  public void fetchBooking(User user1) {
    user1.printTickets();
  }

  public void cancelBooking(User user, String ticketId) {
    List<Ticket> userBookedTickets = user.getTicketsBooked();
    Boolean validTicket = false;
    for (Ticket t : userBookedTickets) {
      if (t.getTicketId().equals(ticketId)) {
        validTicket = true;
        break;
      }
    }
    if (!validTicket) {
      System.out.println("Not your valid ticket");
      return;
    }
    trainService.cancelBooking(user, ticketId);
  }

  public List<Train> getTrains(String source, String destination) {
    return trainService.searchTrains(source, destination);
  }

  public void bookTickets(String trainNo, String source, String destination) {
    Train train = trainService.getTrainByNumber(trainNo);
    if (train == null) {
      System.out.println("Invalid Train Number");
      return;
    }
    Ticket bookedTicket = trainService.bookSeats(train, user, source, destination);
    if (bookedTicket != null) {
      user.getTicketsBooked().add(bookedTicket);
      for (User u : userList) {
        if (u.getName().equals(user.getName())) {
          u.setTicketsBooked(user.getTicketsBooked());
          break;
        }
      }
      try {
        saveUserListToFile();
      } catch (IOException ex) {
        System.out.println("Failed to save user list: " + ex.getMessage());
      }
      System.out.println("Tickets booked successfully");
    } else {
      System.out.println("Failed to book your tickets");
    }
  }
}
