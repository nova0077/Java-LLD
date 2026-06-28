package gateway.routing;

import gateway.models.ApiRequest;
import gateway.throttling.RateLimitStrategy;

public class WebSdkRouter extends WorkzoneRouter {
  public WebSdkRouter(RateLimitStrategy rateLimiter) {
    super(rateLimiter);
  }

  @Override
  protected void doRoute(ApiRequest request) throws Exception {
    System.out.println("[Routing] WebSdk Workzone: ");
  }
}
