package gateway;

import gateway.metrics.GlobalMetricsTracker;
import gateway.models.ApiRequest;
import gateway.routing.RouterFactory;
import gateway.routing.WorkzoneRouter;

public class Main {
  public static void main(String[] args) {
    ApiRequest req1 = new ApiRequest("praveen123", "/websdk/config", "{domain: 'lenote'}");
    ApiRequest req2 = new ApiRequest("novaa", "/core-entities/settings", "{domain: 'lenote'}");
    ApiRequest req3 = new ApiRequest("0077", "/admin/login", "{domain: 'lenote'}");

    System.out.println("Starting API Gateway Engine...\n");

    WorkzoneRouter router1 = RouterFactory.getRouterForPath(req1.getPath());
    router1.processRequest(req1);

    System.out.println("-------------------------------------");
    WorkzoneRouter router2 = RouterFactory.getRouterForPath(req2.getPath());
    router2.processRequest(req2);

    System.out.println("-------------------------------------");
    WorkzoneRouter router3 = RouterFactory.getRouterForPath(req3.getPath());
    router3.processRequest(req3);

    GlobalMetricsTracker.getInstance().printStats();
  }
}
