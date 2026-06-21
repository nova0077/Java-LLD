package models;

public class User {
  // avoiding login & signup for now, so just a username and name
  private String username;
  private String name;
  
  public User(String username, String name) {
    this.username = username;
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public String getName() {
    return name;
  }
}
