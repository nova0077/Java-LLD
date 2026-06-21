package buttons;

import services.ElevatorController;

public class ElevatorButton implements Button {
  // Inside the elevator, each button represents the floor number
  private int destinationFloor;
  private ElevatorController controller;

  public ElevatorButton(int destinationFloor, ElevatorController controller) {
    this.destinationFloor = destinationFloor;
    this.controller = controller;
  }

  @Override
  public void press() {
    System.out.println("Elevator button for floor " + destinationFloor + " pressed.");
    controller.requestElevator(destinationFloor);
  }
}