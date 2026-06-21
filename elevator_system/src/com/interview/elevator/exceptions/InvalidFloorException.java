package exceptions;

public class InvalidFloorException extends RuntimeException {

  public InvalidFloorException() {
    super("The requested floor does not exist in the building");
  }

  public InvalidFloorException(String message) {
    super(message);
  }

  public InvalidFloorException(int invalidFloor) {
    super("Invalid request: Floor " + invalidFloor + " does not exist");
  }
}
