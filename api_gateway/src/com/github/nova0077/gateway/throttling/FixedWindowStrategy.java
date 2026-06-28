package gateway.throttling;

public class FixedWindowStrategy implements RateLimitStrategy {
  public boolean isAllowed(String clientId) {
    System.out.println("RateLimitStrategy is applied on clientId: " + clientId);
    if (clientId.length() % 2 == 0) {
      return true;
    }
    return false;
  }
}