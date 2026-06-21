package buttons;

import enums.Direction;
import services.ElevatorController;

public class HallButton implements Button {
  private int sourceFloor;
  private Direction destinationDirection;

  private ElevatorController controller;

  public HallButton(int sourceFloor, Direction destinationDirection, ElevatorController controller) {
    this.sourceFloor = sourceFloor;
    this.destinationDirection = destinationDirection;
    this.controller = controller;
  }

  @Override
  public void press() {
    System.out.println("Hall button for direction " + destinationDirection + " pressed.");
    controller.requestElevator(sourceFloor);
  }
}
