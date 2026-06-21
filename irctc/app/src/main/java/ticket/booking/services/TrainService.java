package main.java.ticket.booking.services;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import main.java.ticket.booking.entities.Train;
import main.java.ticket.booking.entities.User;
import main.java.ticket.booking.entities.Ticket;

public class TrainService {
  private List<Train> trainList;

  private static final String TRAINS_PATH = "/Users/praveen/Desktop/code/java/irctc/app/src/main/java/ticket/booking/localDb/trains.json";
  private static ObjectMapper objectMapper = new ObjectMapper();

  Scanner scanner = new Scanner(System.in);

  public List<Train> loadTrains() throws IOException {
    File trains = new File(TRAINS_PATH);
    return objectMapper.readValue(trains, new TypeReference<List<Train>>() {
    });
  }

  public TrainService() throws IOException {
    trainList = loadTrains();
  }

  public List<Train> searchTrains(String source, String destination) {
    return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
  }

  private boolean validTrain(Train train, String source, String destination) {
    List<String> stationOrder = train.getStations();
    int sourceInd = stationOrder.indexOf(source.toLowerCase());
    int destinationInd = stationOrder.indexOf(destination.toLowerCase());
    return sourceInd != -1 && destinationInd != -1 && sourceInd < destinationInd;
  }

  // returns booked Ticket objects, in case of error it will be empty
  public Ticket bookSeats(Train train, User user, String source, String destination) {
    List<List<Integer>> seats = train.getSeats();

    Integer availableSeatsCount = 0;
    for (int i = 0; i < seats.size(); i++) {
      List<Integer> innerList = seats.get(i);
      for (int j = 0; j < innerList.size(); j++) {
        if (innerList.get(j) == 0) {
          availableSeatsCount++;
        }
      }
    }

    System.out.println("Available number of seats: " + availableSeatsCount);
    System.out.println("Enter number of seats to book: ");
    Integer noOfSeats = scanner.nextInt();

    System.out.println("Available seat are: ");
    for (int i = 0; i < seats.size(); i++) {
      List<Integer> innerList = seats.get(i);
      for (int j = 0; j < innerList.size(); j++) {
        if (innerList.get(j) == 0) {
          System.out.printf("row %d, column %d: \n", i, j);
        }
      }
    }
    int booked = 0;
    Set<List<Integer>> bookedSeats = new HashSet<>();

    while (booked < noOfSeats) {
      System.out.println("Enter '-1' to quit: ");
      System.out.println("Enter seat row: ");
      int row = scanner.nextInt();
      if (row == -1)
        return null;
      System.out.println("Enter seat column: ");
      int col = scanner.nextInt();

      if (row >= 0 && row < seats.size() && col >= 0 && col < seats.get(row).size()) {
        if (seats.get(row).get(col) == 0) {
          booked++;
          bookedSeats.add(Arrays.asList(row, col));
          System.out.println("Seat added for booking");
        } else {
          System.out.println("Seat is already booked. Choose another.");
        }
      } else {
        System.out.println("Invalid seat number. Try again.");
      }
    }

    System.out.println("Enter y/n to confirm your booking: ");
    if (scanner.next().equals("y")) {
      for (List<Integer> p : bookedSeats) {
        int row = p.get(0);
        int col = p.get(1);
        seats.get(row).set(col, 1); // Mark seat as booked
      }

      String ticketId = UUID.randomUUID().toString();
      Ticket ticket = new Ticket(ticketId, user.getUserId(), source, destination, train.getTrainId(), bookedSeats);

      // Update trains.json with the new seat booking
      try {
        List<Train> allTrains = objectMapper.readValue(new File(TRAINS_PATH), new TypeReference<List<Train>>() {
        });
        for (Train t : allTrains) {
          if (t.getTrainId().equals(train.getTrainId())) {
            t.setSeats(seats);
            break;
          }
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(TRAINS_PATH), allTrains);
      } catch (IOException e) {
        System.out.println("Failed to update trains.json: " + e.getMessage());
        bookedSeats.clear();
      }
      return ticket;
    } else {
      System.out.println("Booking stopped successfully!");
      bookedSeats.clear();
    }
    return null;
  }

  public Train getTrainByNumber(String trainNo) {
    for (Train t : trainList) {
      if (t.getTrainNo().equals(trainNo)) {
        return t;
      }
    }
    return null;
  }

  public Boolean cancelBooking(User user, String ticketId) {
    try {
      File usersFile = new File(
          "/Users/praveen/Desktop/code/java/irctc/app/src/main/java/ticket/booking/localDb/users.json");
      List<User> users = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {
      });

      boolean found = false;
      for (User u : users) {
        if (u.getUserId().equals(user.getUserId())) {
          List<Ticket> tickets = u.getTicketsBooked();
          for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equals(ticketId) && !"canceled".equals(ticket.getStatus())) {
              ticket.setStatus("canceled");
              found = true;

              // Unbook seat in train
              File trainsFile = new File(TRAINS_PATH);
              List<Train> trains = objectMapper.readValue(trainsFile, new TypeReference<List<Train>>() {
              });

              for (Train t : trains) {
                if (t.getTrainId().equals(ticket.getTrainId())) {
                  for (List<Integer> p : ticket.getSeatsBooked()) {
                    int row = p.get(0);
                    int col = p.get(1);
                    t.getSeats().get(row).set(col, 0);
                  }
                  break;
                }
              }

              objectMapper.writerWithDefaultPrettyPrinter().writeValue(trainsFile, trains);
              break;
            }
          }
          break;
        }
      }
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(usersFile, users);
      System.out.println("Successfully Cancelled your booking");
      return found;
    } catch (Exception e) {
      System.out.println("Cancel booking failed: " + e.getMessage());
      return false;
    }
  }
}
