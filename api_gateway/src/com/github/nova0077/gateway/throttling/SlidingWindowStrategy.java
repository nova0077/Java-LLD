package gateway.throttling;

public class SlidingWindowStrategy implements RateLimitStrategy{
  public boolean isAllowed(String clientId) {
    System.out.println("SlidingWindow Strategy is applied on clientId: " + clientId);
    if (clientId.length() % 2 == 0) {
      return true;
    }
    return false;
  }
}
