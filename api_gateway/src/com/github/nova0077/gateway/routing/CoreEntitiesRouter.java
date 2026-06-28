package gateway.routing;

import gateway.models.ApiRequest;
import gateway.throttling.RateLimitStrategy;

public class CoreEntitiesRouter extends WorkzoneRouter {
  public CoreEntitiesRouter(RateLimitStrategy rateLimiter) {
    super(rateLimiter);
  }

  @Override
  protected void doRoute(ApiRequest request) throws Exception {
    System.out.println("[ROUTING] Core Entities Workzone");
  }
}
