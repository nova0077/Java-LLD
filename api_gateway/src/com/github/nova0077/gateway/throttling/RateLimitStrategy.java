package gateway.throttling;

public interface RateLimitStrategy {
  boolean isAllowed(String clientId);
}
