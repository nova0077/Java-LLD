package main.java.ticket.booking;

import java.io.IOException;
import java.util.*;

import main.java.ticket.booking.entities.User;
import main.java.ticket.booking.entities.Train;
import main.java.ticket.booking.services.UserBookingService;
import main.java.ticket.booking.util.UserServiceUtil;

public class App {
  public static void main(String[] args) {
    System.out.println("Running Train Booking System");
    Scanner scanner = new Scanner(System.in);
    int option = 0;
    UserBookingService userBookingService;
    User currentUser = null; // Track the logged-in user

    try {
      userBookingService = new UserBookingService();
    } catch (IOException ex) {
      System.out.println("Something went wrong: \n" + ex.getMessage());
      return;
    }

    while (option != 6) {
      System.out.println("Choose an option");
      System.out.println("1. Sign up");
      System.out.println("2. Login");
      System.out.println("3. Fetch Bookings");
      System.out.println("4. Book ticket");
      System.out.println("5. Cancel my Booking");
      System.out.println("6. Exit the App");
      option = scanner.nextInt();

      switch (option) {
        case 1:
          System.out.print("Enter your username to signup: ");
          String nametoSignup = scanner.next();
          System.out.print("Enter your password to signup: ");
          String passwordtoSignup = scanner.next();
          User userToSignup = new User(nametoSignup, passwordtoSignup, UserServiceUtil.hashPassword(passwordtoSignup),
              new ArrayList<>(), UUID.randomUUID().toString());
          if (userBookingService.signup(userToSignup)) {
            System.out.println("Signup successful!");
          } else {
            System.out.println("Signup failed!");
          }
          break;

        case 2:
          System.out.print("Enter your username: ");
          String nameToLogin = scanner.next();
          System.out.print("Enter your password: ");
          String passwordToLogin = scanner.next();
          User userToLogin = new User(nameToLogin, passwordToLogin, UserServiceUtil.hashPassword(passwordToLogin),
              new ArrayList<>(), UUID.randomUUID().toString());
          try {
            userBookingService = new UserBookingService(userToLogin);
            User loggedInUser = userBookingService.loginUser();
            if (loggedInUser != null) {
              currentUser = loggedInUser;
              System.out.println("Login successful!");
              System.out.println("Welcome " + currentUser.getName());
            } else {
              System.out.println("Login failed!");
              currentUser = null;
            }
          } catch (IOException ex) {
            System.out.println("Something went wrong during login." + ex.getMessage());
            currentUser = null;
          }
          break;

        case 3:
          if (currentUser == null) {
            System.out.println("Please login first.");
            break;
          }
          System.out.println("Fetching your bookings");
          userBookingService.fetchBooking(currentUser);
          break;

        case 4:
          if (currentUser == null) {
            System.out.println("Please login first.");
            break;
          }
          System.out.println("Enter your source station: ");
          String source = scanner.next();
          System.out.println("Enter your destination station: ");
          String destination = scanner.next();
          List<Train> trains = userBookingService.getTrains(source, destination);
          int index = 1;
          for (Train t : trains) {
            System.out.println(index + " Train number: " + t.getTrainNo());
            for (Map.Entry<String, String> entry : t.getStationTimes().entrySet()) {
              System.out.println("station: " + entry.getKey() + ", time: " + entry.getValue());
            }
            System.out.println("Available no.of seats: " + t.getCountOfAvailableSeats(t.getSeats()));
            index++;
          }

          System.out.println("Continue booking? enter 'y/n': ");
          String bookingChoice = scanner.next();
          if (bookingChoice.equals("n"))
            break;
          System.out.print("Enter your chosen train number: ");
          String chosenTrainNo = scanner.next();
          userBookingService.bookTickets(chosenTrainNo, source, destination);
          break;

        case 5:
          if (currentUser == null) {
            System.out.println("Please login first.");
            break;
          }
          System.out.println("Your bookings are");
          userBookingService.fetchBooking(currentUser);
          System.out.print("Enter your ticketId to cancel: ");
          String ticketIdToCancel = scanner.next();
          userBookingService.cancelBooking(currentUser, ticketIdToCancel);
          break;

        default:
          break;
      }
    }
  }
}
