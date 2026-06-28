package gateway.models;

public class ApiRequest {
  String clientId;
  String path;
  String payload;

  public ApiRequest(String clientId, String route, String payload) {
    this.clientId = clientId;
    this.path = route;
    this.payload = payload;
  }

  public String getPath() {
    return path;
  }

  public String getClientId() {
    return clientId;
  }

  public String getPayload() {
    return payload;
  }
}
