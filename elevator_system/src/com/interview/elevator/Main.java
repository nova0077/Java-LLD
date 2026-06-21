import java.util.*;

import buttons.ElevatorButton;
import buttons.HallButton;
import enums.Direction;
import exceptions.InvalidFloorException;
import models.ElevatorCar;
import services.ElevatorController;

public class Main {
  public static void main(String[] args) {
    List<ElevatorCar> elevatorCars = new ArrayList<>();
    int totalElevators = 3;
    for (int i = 0; i < totalElevators; i++) {
      ElevatorCar eCar = new ElevatorCar(i);
      elevatorCars.add(eCar);
    }

    ElevatorController controller = new ElevatorController(elevatorCars);
    System.out.println("Elevator Controller is ready, waiting for requests");

    try {
      System.out.println("[Scenario 1] Passenger on Floor 3 presses UP button in the hallway.");
      HallButton floor3DownDButton = new HallButton(3, Direction.DOWN, controller);
      floor3DownDButton.press();

      System.out.println("--------------------------------------------------");

      System.out.println("[Scenario 2] Passenger inside elevator presses destination floor");
      ElevatorButton e1Button = new ElevatorButton(1, controller);
      e1Button.press();
      System.out.println("--------------------------------------------------");

      // --- SCENARIO 3: Simultaneous Requests ---
      System.out.println("[Scenario 3] Two people press buttons on different floors at the same time.");
      HallButton floor9DownButton = new HallButton(9, Direction.DOWN, controller);
      HallButton floor2UpButton = new HallButton(2, Direction.UP, controller);
      floor9DownButton.press();
      floor2UpButton.press();
    } catch (InvalidFloorException e) {
      System.out.println("EXCEPTION CAUGHT: " + e.getMessage());
    }

    System.out.println("Stimulation is completed");
  }

}