package models;
import enums.State;
import enums.Direction;

public class ElevatorCar {
  int id;
  int currentFloor;
  State state;
  Direction direction;
  // Optimisation: Add timestamp when process is assigned to track the currentFloor more accurately
  // optionally we could have capacity, list of passengers

  public ElevatorCar(int id) {
    this.id = id;
    this.state = State.IDLE;
    this.direction = Direction.NONE;
  }

  public int getId() {
    return id;
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

  public State getState() {
    return state;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setState(State state) {
    this.state = state;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public void setCurrentFloor (int floorNumber) {
    this.currentFloor = floorNumber;
  }
}
