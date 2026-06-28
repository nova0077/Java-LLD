package gateway.routing;

import gateway.throttling.FixedWindowStrategy;
import gateway.throttling.SlidingWindowStrategy;
import gateway.throttling.TokenBucketStrategy;

public class RouterFactory {
  // should return the router Obj
  public static WorkzoneRouter getRouterForPath(String path) {
    if (path.contains("/websdk")) {
      return new WebSdkRouter(new SlidingWindowStrategy());
    } else if (path.contains("/admin")) {
      return new AdminRouter(new FixedWindowStrategy());
    } else if (path.contains("/core-entities")) {
      return new CoreEntitiesRouter(new TokenBucketStrategy());
    } else {
      throw new IllegalArgumentException("Unknown domain path: " + path);
    }
  }
}