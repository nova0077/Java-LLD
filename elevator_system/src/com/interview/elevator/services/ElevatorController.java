package services;

import java.util.*;

import enums.Direction;
import enums.State;
import models.ElevatorCar;

public class ElevatorController {
  private List<ElevatorCar> elevatorCars = new ArrayList<>();
  private Queue<Integer> floorRequestQueue = new LinkedList<>();

  public ElevatorController(List<ElevatorCar> elevatorCars) {
    this.elevatorCars = elevatorCars;
  }

  public void requestElevator(int floorNumber) {
    floorRequestQueue.add(floorNumber);
    processRequests();
  }

  public ElevatorCar findBestElevatorCar(int floorNumber) {
    for (ElevatorCar elevator : elevatorCars) {
      // TODO: Add logic of intermediate requests,
      // Valid case: elvator moving up & targetFloor > currentFloor
      if (elevator.getDirection() == Direction.NONE || elevator.getState() == State.IDLE) {
        return elevator;
      }
    }
    return null;   
  }

  public void moveElevator(ElevatorCar elevatorCar, int targetFloor) {
    System.out.println("Moving Elevator " + elevatorCar.getId() + " to floor " + targetFloor);
    int startingFloor = elevatorCar.getCurrentFloor();

    if (startingFloor == targetFloor)
      return;
    // move elevator to destination
    if (startingFloor < targetFloor)
      elevatorCar.setDirection(Direction.UP);
    else
      elevatorCar.setDirection(Direction.DOWN);
    elevatorCar.setState(State.MOVING);
    elevatorCar.setCurrentFloor(targetFloor);
    elevatorCar.setState(State.DOORS_OPEN);
    elevatorCar.setDirection(Direction.NONE);
    elevatorCar.setState(State.IDLE);
  }

  public void processRequests() {
    if (floorRequestQueue.isEmpty()) {
      return;
    }

    while (!floorRequestQueue.isEmpty()) {
      int requestedFloor = floorRequestQueue.peek();

      // TODO: Add retry + wait timeout logic here
      ElevatorCar bestElevator = findBestElevatorCar(requestedFloor);

      if (bestElevator != null) {
        floorRequestQueue.poll();
        moveElevator(bestElevator, requestedFloor);
      } else {
        System.out.println("All the elevatorCars are busy, please try later");  
      }
    }
  }
}