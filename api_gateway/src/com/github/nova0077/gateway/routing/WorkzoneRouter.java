package gateway.routing;

import gateway.metrics.GlobalMetricsTracker;
import gateway.models.ApiRequest;
import gateway.throttling.RateLimitStrategy;

public abstract class WorkzoneRouter {
  protected RateLimitStrategy rateLimiter; // Dependency Injection
  protected GlobalMetricsTracker metrics = GlobalMetricsTracker.getInstance();

  public WorkzoneRouter(RateLimitStrategy rateLimiter) {
    this.rateLimiter = rateLimiter;
  }

  public void processRequest(ApiRequest request) {
    System.out.println("[Routing] Processing request for path: " + request.getPath());

    if (!rateLimiter.isAllowed(request.getClientId())) {
      System.out.println("[ROUTING] Request throttled for client: " + request.getClientId());
      metrics.incrementThrottled();
      return;
    }

    try {
      doRoute(request);
      metrics.incrementRouted();
    } catch (Exception e) {
      System.out.println("[ROUTING] Internal failure " + request.getPath());
      sendToDeadLetterQueue(request);
      metrics.incrementError();
    }
  }

  protected abstract void doRoute(ApiRequest request) throws Exception;

  private void sendToDeadLetterQueue(ApiRequest request) {
    System.out.println("[DLQ] Safely parked request for retry" + request.getClientId());
  }
}
