package models;

import java.util.*;

public class Document {
  private String id;
  private String title;
  private ArrayList<Block> content;
  private User owner;
  private ArrayList<User> collaborators;

  public Document(String title, User owner) {
    id = UUID.randomUUID().toString();
    this.title = title;
    this.owner = owner;
    this.content = new ArrayList<>();
    this.collaborators = new ArrayList<>();
    collaborators.add(owner);
  }

  public void addCollaborator(User user) {
    collaborators.add(user);
  }

  public void changeTitle(String newTitle) {
    this.title = newTitle;
  }

  public void addContent(Block block) {
    content.add(block);
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public ArrayList<Block> getContent() {
    return content;
  }

  public User getOwner() {
    return owner;
  }

  public Boolean isValidCollaborator(User user) {
    return collaborators.contains(user);
  }
}