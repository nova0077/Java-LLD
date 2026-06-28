package gateway.metrics;

public class GlobalMetricsTracker {
  private static final GlobalMetricsTracker metrics = new GlobalMetricsTracker();

  private int routedCount = 0;
  private int throttledCount = 0;
  private int errorCount = 0;

  private GlobalMetricsTracker() {
  }

  public static GlobalMetricsTracker getInstance() {
    return metrics;
  }

  public synchronized void incrementRouted() {
    routedCount++;
  }

  public synchronized void incrementThrottled() {
    throttledCount++;
  }

  public synchronized void incrementError() {
    errorCount++;
  }

  public void printStats() {
    System.out.println("\n=== [GATEWAY METRICS] ===");
    System.out.println("Total Successfully Routed: " + routedCount);
    System.out.println("Total Throttled (Blocked): " + throttledCount);
    System.out.println("Total Errors (Sent to DLQ): " + errorCount);
    System.out.println("==========================\n");
  }
}
