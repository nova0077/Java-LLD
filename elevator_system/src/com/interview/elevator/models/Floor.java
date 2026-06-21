package models;

public class Floor {
  int floorNumber;
  // Additional attributes can be added: elevatorStats, HallButtonState, etc.

  public Floor(int floorNumber) {
    this.floorNumber = floorNumber;
  }

  public int getFloorNumber() {
    return floorNumber;
  }

  public void setFloorNumber(int floorNumber) {
    this.floorNumber = floorNumber;
  }
}
