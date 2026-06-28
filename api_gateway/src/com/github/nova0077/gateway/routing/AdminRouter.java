package gateway.routing;

import gateway.models.ApiRequest;
import gateway.throttling.RateLimitStrategy;

public class AdminRouter extends WorkzoneRouter {
  public AdminRouter(RateLimitStrategy rateLimiter) {
    super(rateLimiter);
  }

  @Override
  protected void doRoute(ApiRequest request) {
    System.out.println("[ROUTER]: Admin workzone");
  }
}